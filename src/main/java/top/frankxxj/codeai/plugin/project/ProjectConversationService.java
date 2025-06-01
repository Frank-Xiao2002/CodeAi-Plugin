package top.frankxxj.codeai.plugin.project;

import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.ResourceAccessException;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;
import top.frankxxj.codeai.plugin.entity.ChatSession;
import top.frankxxj.codeai.plugin.entity.dto.HistoryRequestDTO;
import top.frankxxj.codeai.plugin.settings.AppSettings;
import top.frankxxj.codeai.plugin.utils.BallonUtils;
import top.frankxxj.codeai.plugin.utils.HttpUtils;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import java.util.*;

@Service(Service.Level.PROJECT)
@State(name = "top.frankxxj.codeai.plugin.project.ProjectConversationService",
        storages = {@Storage(StoragePathMacros.WORKSPACE_FILE)})
public final class ProjectConversationService implements PersistentStateComponent<ProjectConversationService.State> {
    private static final Logger log = Logger.getInstance(ProjectConversationService.class);

    @Setter
    private String currentConversationId = "";
    private Map<String, List<ChatSession>> history = new HashMap<>();

    private State myState = new State();

    @Override
    public @Nullable State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    public static class State {
        public List<String> conversationIds;

        public State() {
            conversationIds = new ArrayList<>();
        }
    }

    public List<String> getAllConversationIds() {
        return myState.conversationIds;
    }

    public void removeConversation(String conversationId) {
        myState.conversationIds.remove(conversationId);
        log.info("Removed conversation: " + conversationId);
        if (myState.conversationIds.isEmpty()) {
            createConversation();
        }
    }

    public String createConversation() {
        String userId = Objects.requireNonNull(AppSettings.getInstance().getState()).userId;
        String conversationId = String.format("%s-%s", userId, System.currentTimeMillis());
        myState.conversationIds.add(conversationId);
        log.info("Created conversation: " + conversationId);
        return conversationId;
    }

    public void loadHistory(Project project) {
        if (myState.conversationIds.isEmpty()) {
            createConversation();

        } else {
            var headers = new HttpHeaders();
            headers.setBearerAuth(TokenUtils.retrieveToken());
            new Thread(() -> {
                for (String conversationId : myState.conversationIds) {
                    var requestEntity = RequestEntity
                            .get(Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/conversation/{id}", conversationId)
                            .headers(headers)
                            .build();
                    if (conversationId.split("-")[0].equals(Objects.requireNonNull(AppSettings.getInstance().getState()).userId)) {
                        //this conversation belongs to the current user
                        List<HistoryRequestDTO> response = List.of();
                        try {
                            response = HttpUtils.restTemplate.exchange(
                                    requestEntity, new ParameterizedTypeReference<List<HistoryRequestDTO>>() {
                                    }).getBody();
                            BallonUtils.showInfoBallon("Conversation history loaded successfully.");
                        } catch (ResourceAccessException e) {
                            BallonUtils.showWarnBallon("Cannot connect to the server.");
                        }
                        for (var item : response) {
                            var chatSession = new ChatSession(conversationId, item.messageType(), item.text());
                            history.computeIfAbsent(conversationId, k -> new ArrayList<>()).add(chatSession);
                        }
                    }
                }
                var sessionPanel = project.getService(ProjectComponentManager.class).getComponent(VerticalStackWrappingPanel.NAME);
                ((VerticalStackWrappingPanel) sessionPanel).resetContent(project);
            }).start();
        }
    }

    public String getCurrentConversationId() {
        if (currentConversationId.isEmpty()) {
            currentConversationId = myState.conversationIds.getFirst();
        }
        return currentConversationId;
    }

    public List<ChatSession> getSessions(String conversationId) {
        return history.getOrDefault(conversationId, new ArrayList<>());
    }

    public void addSession(String conversationId, ChatSession session) {
        history.computeIfAbsent(conversationId, k -> new ArrayList<>()).add(session);
    }
}
