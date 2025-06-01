package top.frankxxj.codeai.plugin.chat.message.codeblock;

import top.frankxxj.codeai.plugin.ui.component.KeyboardAccessibleActionButton;

import javax.swing.*;
import java.awt.*;

public class CodeBlockControlPanel extends JPanel {
    private JPanel buttonPanel;
    private KeyboardAccessibleActionButton copyActionButton;
    private KeyboardAccessibleActionButton insertCodeAtCaretActionButton;

    public CodeBlockControlPanel() {
        this.buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        // TODO: 5/25/2025 setup copyActionButton
        this.copyActionButton = null;
        buttonPanel.add(copyActionButton);
        add(buttonPanel);
    }
}
