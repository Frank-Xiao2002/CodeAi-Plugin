package top.frankxxj.codeai.plugin.chat.message;

import com.intellij.util.ui.JBUI;

import javax.swing.*;

public class MessageComponent extends JComponent {
    private JPanel topLinePanel;
    private JPanel messagePanel;
    private JPanel messageCenterPanel;

    public MessageComponent() {
        setBorder(JBUI.Borders.empty(1, 0, 20, 0));

    }
}
