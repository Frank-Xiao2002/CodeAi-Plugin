package top.frankxxj.codeai.plugin.chat.message.codeblock;

import javax.swing.*;
import java.awt.*;

public class CodeBlockPanel extends JLayeredPane {
    private JPanel controlPanelContainer;
    private JPanel controlPanel;
    private CodeBlockPanel.CodeBlockDisplayPanel codeBlockDisplayPanel;
    private JPanel codeBlock;

    public CodeBlockPanel(Container container) {
        controlPanelContainer = new JPanel();
        controlPanelContainer.add(new CodeBlockControlPanel());
        setLayout(new OverlayLayout(container));
        setAlignmentX(0.4f);
        setAlignmentY(0.4f);
        add(controlPanelContainer);
        add(codeBlockDisplayPanel);
    }

    private static class CodeBlockDisplayPanel extends JPanel {
    }
}
