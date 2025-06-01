package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class CodeAiChatInputToolbarPanel extends JPanel {
    private static final Dimension preferredSize = new Dimension(191, 44);
    private static final Dimension maxSize = new Dimension(2147483647, 40);

    public CodeAiChatInputToolbarPanel() {
        super();
        setBackground(new JBColor(new Color(0xFFDFE1E5), new Color(0xFF393B40)));
        setLayout(new GridBagLayout());
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setOpaque(true);
        setBorder(JBUI.Borders.empty(0, 9));
        setMinimumSize(preferredSize);
        setPreferredSize(preferredSize);
        setMaximumSize(maxSize);

    }
}
