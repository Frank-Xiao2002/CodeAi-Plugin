package top.frankxxj.codeai.plugin.chat.session.component;

import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.components.JBScrollPane;
import top.frankxxj.codeai.plugin.chat.input.CodeAiChatInputPanel;
import top.frankxxj.codeai.plugin.chat.panels.ActionButtonsPanel;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;
import top.frankxxj.codeai.plugin.chat.session.actions.DeleteAction;
import top.frankxxj.codeai.plugin.chat.window.actions.ConversationHistoryAction;
import top.frankxxj.codeai.plugin.chat.window.actions.NewConversationAction;
import top.frankxxj.codeai.plugin.icons.CodeAiIcons;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;
import top.frankxxj.codeai.plugin.settings.AppSettings;
import top.frankxxj.codeai.plugin.ui.component.KeyboardAccessibleActionButton;

import javax.swing.*;
import java.awt.*;

public class ChatSessionComponent extends JPanel {
    ChatSessionToolBarComponent toolBarComponent;
    OnePixelSplitter splitter = new OnePixelSplitter(true);
    CodeAiChatInputPanel chatInputPanel;
    VerticalStackWrappingPanel messagePanel;
    KeyboardAccessibleActionButton add, viewHistory, delete;
    SecondaryTextLabel currentConversationLabel = new SecondaryTextLabel();

    public ChatSessionComponent(Project project) {
        project.getService(ProjectComponentManager.class).registerComponent(SecondaryTextLabel.NAME, currentConversationLabel);
        setPreferredSize(new Dimension(219, 334));
        setLayout(new BorderLayout(0, 0));
        var toolbarActionSection = new ActionButtonsPanel();
        Dimension buttonSize = new Dimension(26, 22);
        viewHistory = new KeyboardAccessibleActionButton(new ConversationHistoryAction(),
                new Presentation("Conversation History"), "ChatSession1", buttonSize);
        add = new KeyboardAccessibleActionButton(new NewConversationAction(),
                new Presentation("New Conversation"), "ChatSession1", buttonSize);
        delete = new KeyboardAccessibleActionButton(new DeleteAction(),
                new Presentation("Delete Current Conversation"), "ChatSession1", buttonSize);
        viewHistory.getPresentation().setIcon(CodeAiIcons.history);
        add.getPresentation().setIcon(CodeAiIcons.new_thread);
        delete.getPresentation().setIcon(CodeAiIcons.delete);
        toolbarActionSection.add(viewHistory);
        toolbarActionSection.add(add);
        toolbarActionSection.add(delete);
        var currentConversationid = project.getService(ProjectConversationService.class).getCurrentConversationId();
        currentConversationLabel.setText(currentConversationid);
        toolBarComponent = new ChatSessionToolBarComponent(toolbarActionSection, currentConversationLabel);
        add(splitter, BorderLayout.CENTER);
        add(toolBarComponent, BorderLayout.NORTH);
        chatInputPanel = new CodeAiChatInputPanel(project);
        messagePanel = new VerticalStackWrappingPanel(project);
        splitter.setFirstComponent(new JBScrollPane(messagePanel));
        splitter.setSecondComponent(chatInputPanel);
        setComponentEnabled();
    }

    private void setComponentEnabled() {
        boolean loggedIn = AppSettings.getInstance().isLoggedIn();
        System.out.println("loggedIn = " + loggedIn);
        add.setEnabled(loggedIn);
        viewHistory.setEnabled(loggedIn);
        delete.setEnabled(loggedIn);
    }


}
