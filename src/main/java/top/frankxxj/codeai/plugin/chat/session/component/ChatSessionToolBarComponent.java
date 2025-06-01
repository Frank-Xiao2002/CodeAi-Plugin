package top.frankxxj.codeai.plugin.chat.session.component;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import top.frankxxj.codeai.plugin.chat.panels.ActionButtonsPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ChatSessionToolBarComponent extends JPanel {
    private ActionButtonsPanel toolbarActionSection;
    private JComponent leftComponent;

    public ChatSessionToolBarComponent(ActionButtonsPanel toolbarActionSection,
                                       JComponent leftComponent) {
        this.toolbarActionSection = toolbarActionSection;
        this.leftComponent = leftComponent;
        setLayout(new BorderLayout(0, 0));
        setBorder(new CompoundBorder(new MatteBorder(1, 0, 1, 0, new JBColor(0xd3d5db, 0xFFACADAF)),
                JBUI.Borders.empty(7, 9)));
        JPanel toolbarPanel = new JPanel(new BorderLayout(0, 0));
        add(toolbarPanel, BorderLayout.CENTER);
        toolbarPanel.add(this.toolbarActionSection, BorderLayout.EAST);
        toolbarPanel.add(this.leftComponent, BorderLayout.WEST);
    }
}
