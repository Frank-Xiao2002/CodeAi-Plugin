package top.frankxxj.codeai.plugin.chat.message;

import com.intellij.ui.JBColor;
import top.frankxxj.codeai.plugin.entity.ChatSession;

import java.awt.*;

public class UserMessageComponent extends MessageComponent {
    public UserMessageComponent(ChatSession session, String username) {
        super(session, username);
        setBackground(new JBColor(new Color(0xFFEBECF0), new Color(0xFF393B40)));
    }
}
