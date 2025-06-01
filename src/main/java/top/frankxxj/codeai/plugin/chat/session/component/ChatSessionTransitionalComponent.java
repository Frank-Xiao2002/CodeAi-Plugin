package top.frankxxj.codeai.plugin.chat.session.component;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;

import javax.swing.*;
import java.awt.*;

public class ChatSessionTransitionalComponent extends JPanel {
    public static final String NAME = "ChatSessionTransitionalComponent";
    JBScrollPane chatSessionScrollablePanel = new JBScrollPane();
    final ChatSessionComponent chatSessionComponent;
    JPanel loadingPanel = new JPanel();
    ViewAllConversationComponent viewAllPanel;

    public ChatSessionTransitionalComponent(Project project) {
        viewAllPanel = new ViewAllConversationComponent(project);
        project.getService(ProjectComponentManager.class).registerComponent(NAME, this);
        setPreferredSize(new Dimension(519, 336));
        chatSessionScrollablePanel.setPreferredSize(new Dimension(221, 336));
        CardLayout cardLayout = new CardLayout(0, 0);
        setLayout(cardLayout);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        add(chatSessionScrollablePanel, "ChatSessionScrollablePanel");
        add(viewAllPanel, "viewAllPanel");
        chatSessionComponent = new ChatSessionComponent(project);
        chatSessionScrollablePanel.setViewportView(chatSessionComponent);
        showChatSession();
    }

    public void showChatSession() {
        CardLayout cardLayout = (CardLayout) getLayout();
        cardLayout.show(this, "ChatSessionScrollablePanel");
    }

    public void showViewAllConversation() {
        CardLayout cardLayout = (CardLayout) getLayout();
        cardLayout.show(this, "viewAllPanel");
    }

}
