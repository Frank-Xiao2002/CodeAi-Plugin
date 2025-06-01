package top.frankxxj.codeai.plugin.chat.user;

import com.intellij.icons.AllIcons;

import javax.swing.*;

public class CodeAiUserIcon extends JLabel {
    public CodeAiUserIcon() {
        setIcon(AllIcons.General.User);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }

}
