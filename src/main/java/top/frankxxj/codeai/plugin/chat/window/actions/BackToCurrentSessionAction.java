package top.frankxxj.codeai.plugin.chat.window.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;
import top.frankxxj.codeai.plugin.chat.session.component.ChatSessionTransitionalComponent;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;

public class BackToCurrentSessionAction extends DumbAwareAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var component = e.getProject().getService(ProjectComponentManager.class).getComponent(ChatSessionTransitionalComponent.NAME);
        ((ChatSessionTransitionalComponent) component).showChatSession();
    }
}
