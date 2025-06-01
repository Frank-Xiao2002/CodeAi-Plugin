package top.frankxxj.codeai.plugin.chat.session.component;

import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;
import top.frankxxj.codeai.plugin.icons.CodeAiIcons;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatSessionOption extends JPanel {
    private final String conversationId;
    private static final Color FOCUS_COLOR = new JBColor(new Color(0xFFEDF3FF), new Color(0xFF393B40));

    private JLabel textComponent;

    public ChatSessionOption(Project project, String conversationId) {
        this.conversationId = conversationId;
        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        textComponent = new JLabel(conversationId);
        textComponent.setAlignmentX(0.0f);
        JLabel label = new JLabel(CodeAiIcons.thread);
        label.setBorder(JBUI.Borders.empty(0, 12, 0, 6));
        add(label, BorderLayout.WEST);
        add(textComponent, BorderLayout.CENTER);
        addListeners(project);
    }

    private void addListeners(Project project) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var componentManager = project.getService(ProjectComponentManager.class);
                var chatSessionTransitionalComponent = componentManager.getComponent(ChatSessionTransitionalComponent.NAME);
                var verticalStackPanel = componentManager.getComponent(VerticalStackWrappingPanel.NAME);
                project.getService(ProjectConversationService.class).setCurrentConversationId(conversationId);
                ((SecondaryTextLabel) componentManager.getComponent(SecondaryTextLabel.NAME)).setText(conversationId);
                ((ChatSessionTransitionalComponent) chatSessionTransitionalComponent).showChatSession();
                ((VerticalStackWrappingPanel) verticalStackPanel).resetContent(project);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(FOCUS_COLOR);
                setOpaque(true);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setOpaque(false);
                repaint();
            }
        });
    }
}
