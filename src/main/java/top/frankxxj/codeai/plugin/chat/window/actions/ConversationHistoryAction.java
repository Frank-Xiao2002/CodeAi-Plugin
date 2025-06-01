package top.frankxxj.codeai.plugin.chat.window.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;
import top.frankxxj.codeai.plugin.chat.session.component.ChatSessionTransitionalComponent;
import top.frankxxj.codeai.plugin.chat.session.component.ViewAllConversationComponent;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;

public class ConversationHistoryAction extends DumbAwareAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var componentManager = e.getProject().getService(ProjectComponentManager.class);
        var chatSessionTransitionalComponent = componentManager.getComponent(ChatSessionTransitionalComponent.NAME);
        var vert = componentManager.getComponent(ViewAllConversationComponent.NAME);
        ((ChatSessionTransitionalComponent) chatSessionTransitionalComponent).showViewAllConversation();
        ((ViewAllConversationComponent) vert).refreshConversationList(e.getProject());
    }
}
