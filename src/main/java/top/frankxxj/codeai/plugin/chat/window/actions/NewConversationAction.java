package top.frankxxj.codeai.plugin.chat.window.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;
import top.frankxxj.codeai.plugin.chat.session.component.SecondaryTextLabel;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;

import javax.swing.*;

public class NewConversationAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        var conversationService = project.getService(ProjectConversationService.class);
        var componentManager = project.getService(ProjectComponentManager.class);
        String newConversationId = conversationService.createConversation();
        conversationService.setCurrentConversationId(newConversationId);
        ((SecondaryTextLabel) componentManager.getComponent(SecondaryTextLabel.NAME)).setText(newConversationId);
        JComponent messageStack = componentManager.getComponent(VerticalStackWrappingPanel.NAME);
        ((VerticalStackWrappingPanel) messageStack).resetContent(project);
    }
}
