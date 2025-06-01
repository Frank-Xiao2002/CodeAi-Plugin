package top.frankxxj.codeai.plugin.chat.panels;

import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class ActionButtonsPanel extends JPanel {
    public ActionButtonsPanel() {
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setBorder(JBUI.Borders.emptyLeft(6));
    }
}
