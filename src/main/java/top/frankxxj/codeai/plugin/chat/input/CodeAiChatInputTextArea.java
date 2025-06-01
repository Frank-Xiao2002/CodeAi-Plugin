package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class CodeAiChatInputTextArea extends JBTextArea {
    private static final String placeholderText = "Ask CodeAi anything here...";
    private static final Color placeholderColor = new JBColor(new Color(90, 93, 99), new Color(156, 159, 167));

    private JBPopup suggestionPopup;

    public CodeAiChatInputTextArea() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(JBUI.Borders.empty(11, 11, 12, 11));
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setOpaque(true);
        setFont(JBFont.regular());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(placeholderColor);
            g2.setFont(getFont());
            Insets insets = getInsets();
            g2.drawString(placeholderText, insets.left + 2, getFontMetrics(getFont()).getAscent() + insets.top + 2);
            g2.dispose();
        }
    }


    @SuppressWarnings("unused")
    private void showPopup() {
        if (suggestionPopup != null && suggestionPopup.isVisible()) {
            suggestionPopup.cancel();
        }
        JLabel label = new JLabel("Popup content here");
        suggestionPopup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(label, null)
                .setRequestFocus(false)
                .setResizable(false)
                .createPopup();
        Point location = getLocationOnScreen();
        System.out.println(location);
        suggestionPopup.showInScreenCoordinates(this, new Point(location.x, location.y + getHeight()));
    }
}
