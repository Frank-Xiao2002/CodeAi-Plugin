package top.frankxxj.codeai.plugin.chat.message;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import top.frankxxj.codeai.plugin.entity.ChatSession;

import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CodeAiMessageComponent extends MessageComponent {
    public CodeAiMessageComponent(ChatSession session) {
        super(session,"CodeAi");
        setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, new JBColor(new Color(211, 213, 219), new Color(172, 173, 175))),
                JBUI.Borders.emptyBottom(20)));

    }
}
