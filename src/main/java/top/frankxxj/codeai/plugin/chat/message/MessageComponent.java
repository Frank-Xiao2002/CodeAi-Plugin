package top.frankxxj.codeai.plugin.chat.message;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import top.frankxxj.codeai.plugin.chat.user.CodeAiUserIcon;
import top.frankxxj.codeai.plugin.entity.ChatSession;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class MessageComponent extends JComponent {
    private JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
    private JPanel topLinePanel = new JPanel(new BorderLayout(0, 0));
    private JPanel messagePanel = new JPanel(new BorderLayout(0, 0));
    private JPanel userPanel = new JPanel();
    private JPanel messageCenterContent = new JPanel(new BorderLayout(0, 0));
    private final MessageContentPanel messageContentPanel = new MessageContentPanel();

    public MessageComponent(ChatSession session, String userPanelName) {
        setOpaque(true);
        contentPanel.setOpaque(false);
        topLinePanel.setOpaque(false);
        messagePanel.setOpaque(false);
        setBorder(new CompoundBorder(new MatteBorder(1, 0, 0, 0, new JBColor(0xFFD3D5DB,0xFFACADAF)),
                JBUI.Borders.emptyBottom(20)));
        setLayout(new BorderLayout(0, 0));
        topLinePanel.setBorder(JBUI.Borders.empty(16, 12, 12, 12));
        topLinePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topLinePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.add(new CodeAiUserIcon());
        userPanel.add(new JLabel(userPanelName));
        topLinePanel.add(userPanel, BorderLayout.WEST);
        messagePanel.add(messageCenterContent, BorderLayout.CENTER);
        messageCenterContent.add(messageContentPanel, BorderLayout.CENTER);
        contentPanel.add(topLinePanel, BorderLayout.NORTH);
        contentPanel.add(messagePanel, BorderLayout.CENTER);
        add(contentPanel);
        addMessageContent(session.getContent());
    }

    private void addMessageContent(String markdown) {
        HtmlContentComponent htmlContentComponent = new HtmlContentComponent();
        // TODO: 6/1/2025 build messageContentPanel
        htmlContentComponent.setText(markdown);
        messageContentPanel.add(htmlContentComponent);
    }
}
