package top.frankxxj.codeai.plugin.chat.input;

import javax.swing.*;
import java.awt.*;

public class CodeAiInputToolBarPanel extends JPanel {
    private JPanel rightPanel = new JPanel();

    public CodeAiInputToolBarPanel() {
        setMinimumSize(new Dimension(191, 44));
        setPreferredSize(new Dimension(191, 44));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);

    }
}
