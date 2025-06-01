package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.ui.JBColor;
import com.intellij.ui.RoundedLineBorder;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class CodeAiChatInputPanelWrapper extends JPanel {
    private static final Dimension minimumSize = new Dimension(2, 42);
    private static final Dimension preferredSize = new Dimension(24, 42);

    public CodeAiChatInputPanelWrapper(JTextArea textArea) {
        super();
        setLayout(new BorderLayout(0, 0));
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setBorder(new RoundedLineBorder(new JBColor(new Color(249, 250, 251), new Color(43, 45, 48))));
        JScrollPane jScrollPane = new JBScrollPane(textArea);
        jScrollPane.setMinimumSize(new Dimension(0, 40));
        jScrollPane.setPreferredSize(new Dimension(22, 40));
        add(jScrollPane);
        setMinimumSize(minimumSize);
        setPreferredSize(preferredSize);
    }
}
