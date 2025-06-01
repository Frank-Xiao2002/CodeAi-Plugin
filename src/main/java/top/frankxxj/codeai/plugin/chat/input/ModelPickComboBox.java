package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.openapi.ui.ComboBox;

import java.awt.*;
import java.awt.event.ItemEvent;

public class ModelPickComboBox extends ComboBox<String> {
    public ModelPickComboBox() {
        addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        Object selected = getSelectedItem();
        int width = 10; // default minimum width
        int height = 34; // default height
        if (selected != null) {
            FontMetrics fm = getFontMetrics(getFont());
            width = Math.max(width, fm.stringWidth(selected.toString()) + 50); // 50 for padding and arrow
        }
        return new Dimension(width, height);
    }
}
