package top.frankxxj.codeai.plugin.chat.session.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;
import top.frankxxj.codeai.plugin.chat.session.component.SecondaryTextLabel;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;
import top.frankxxj.codeai.plugin.settings.AppSettings;
import top.frankxxj.codeai.plugin.utils.BallonUtils;
import top.frankxxj.codeai.plugin.utils.HttpUtils;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import java.util.Objects;

/**
 * Action to delete all sessions in one conversation.
 */
public class DeleteAction extends DumbAwareAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        ProjectConversationService conversationService = project.getService(ProjectConversationService.class);
        final String idToDelete = conversationService.getCurrentConversationId();
        conversationService.removeConversation(idToDelete);
        conversationService.setCurrentConversationId("");
        ProjectComponentManager componentManager = project.getService(ProjectComponentManager.class);
        ((SecondaryTextLabel) componentManager.getComponent(SecondaryTextLabel.NAME))
                .setText(conversationService.getCurrentConversationId());
        ((VerticalStackWrappingPanel) componentManager.getComponent(VerticalStackWrappingPanel.NAME)).resetContent(project);
        // TODO: 6/1/2025 setup a new Thread to communicate with midware to delete database records
        new Thread(() -> {
            HttpUtils.webClient
                    .delete()
                    .uri(Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/conversation/{}",
                            idToDelete)
                    .header("Authorization", "Bearer " + TokenUtils.retrieveToken())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            //HttpUtils.restTemplate.exchange(
            //        Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/conversation/{}",
            //        HttpMethod.DELETE,
            //        )
            BallonUtils.showInfoBallon(String.format("Conversation %s deleted successfully.", idToDelete));
        }).start();
    }
}
