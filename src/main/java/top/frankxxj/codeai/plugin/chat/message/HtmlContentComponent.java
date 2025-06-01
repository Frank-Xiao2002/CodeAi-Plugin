package top.frankxxj.codeai.plugin.chat.message;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class HtmlContentComponent extends JEditorPane {
    public HtmlContentComponent() {
        setBorder(JBUI.Borders.empty(0, 12));
        setForeground(new JBColor(new Color(0, 0, 0, 255), new Color(223, 225, 229, 255)));
        setBackground(new JBColor(new Color(255, 255, 255, 255), new Color(69, 73, 74, 255)));
        setFont(JBFont.regular());
        setEditable(false);
        setOpaque(false);
    }
}
