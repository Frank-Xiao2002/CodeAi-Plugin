package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;
import top.frankxxj.codeai.plugin.chat.service.RetrieveInfoService;
import top.frankxxj.codeai.plugin.entity.ChatSession;
import top.frankxxj.codeai.plugin.entity.MessageType;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;
import top.frankxxj.codeai.plugin.settings.AppSettings;
import top.frankxxj.codeai.plugin.utils.BallonUtils;
import top.frankxxj.codeai.plugin.utils.HttpUtils;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import java.util.Objects;

public class SendMessageAction extends DumbAwareAction {
    private final Logger log = Logger.getInstance(SendMessageAction.class);

    public SendMessageAction() {
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var retrieveInfoService = Objects.requireNonNull(e.getProject()).getService(RetrieveInfoService.class);
        String prompt = retrieveInfoService.getPrompt();
        String modelName = retrieveInfoService.getModel();
        if (modelName.endsWith("(pro)")) {
            modelName = modelName.substring(0, modelName.length() - 5).trim();
        }
        if (!prompt.isBlank() && !modelName.isBlank()) {
            if (AppSettings.getInstance().getState().roles.contains("ADMIN") || !modelName.contains("pro")) {
                retrieveInfoService.clearPrompt();
                ProjectConversationService conversationService = e.getProject().getService(ProjectConversationService.class);
                String currentConversationId = conversationService.getCurrentConversationId();
                conversationService.addSession(currentConversationId, new ChatSession(currentConversationId, MessageType.USER, prompt));
                var sessionPanel = e.getProject().getService(ProjectComponentManager.class).getComponent(VerticalStackWrappingPanel.NAME);
                ((VerticalStackWrappingPanel) sessionPanel).resetContent(e.getProject());
                // TODO: 5/31/2025 send prompt
                HttpUtils.webClient.post()
                        .uri(AppSettings.getInstance().getState().serverUrl + "/api/chat/generate")
                        .header("Authorization", "Bearer " + TokenUtils.retrieveToken())
                        .header("Codeai-model", modelName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(retrieveInfoService.buildRequestBody(e, prompt))
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnSuccess(response -> {
                            System.out.println("RECEIVED RESPONSE:" + response);
                            BallonUtils.showInfoBallon("Message sent successfully.");
                            conversationService.addSession(currentConversationId, new ChatSession(currentConversationId, MessageType.ASSISTANT, response));
                            ((VerticalStackWrappingPanel) sessionPanel).resetContent(e.getProject());
                        })
                        .doOnError(error -> {
                            log.error("Error sending message: ", error);
                            BallonUtils.showWarnBallon("Failed to send request: " + error.getMessage());
                        })
                        .subscribe();
            } else {
                BallonUtils.showWarnBallon("You are not allowed to use this model, please buy a pro version.");
            }
        }
    }
}
