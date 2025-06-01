package top.frankxxj.codeai.plugin.chat.panels;

import com.intellij.openapi.ui.DialogPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class WelcomePanel {
    public WelcomePanel() {
        DialogPanel out = new DialogPanel();
        out.setBorder(JBUI.Borders.empty(50, 32, 10, 32));
        out.setOpaque(true);
        out.setLayout(new GridLayout());
        out.setAlignmentX(Component.CENTER_ALIGNMENT);
        out.setAlignmentY(Component.CENTER_ALIGNMENT);
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.weighty = 0.4;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;

        DialogPanel center = new DialogPanel();
        center.setLayout(new GridLayout());

        panel.add(center, c);
        out.add(panel);
    }
}
