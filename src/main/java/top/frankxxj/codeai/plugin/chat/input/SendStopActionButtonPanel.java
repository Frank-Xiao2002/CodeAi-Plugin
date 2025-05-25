package top.frankxxj.codeai.plugin.chat.input;

import top.frankxxj.codeai.plugin.ui.component.KeyboardAccessibleActionButton;

import javax.swing.*;
import java.awt.*;

public class SendStopActionButtonPanel extends JPanel {
    private KeyboardAccessibleActionButton sendActionComponent;
    private KeyboardAccessibleActionButton stopActionComponent;

    public SendStopActionButtonPanel() {
        setLayout(new CardLayout(0, 0));
        setAlignmentX(0.5f);
        setAlignmentY(0.5f);
        sendActionComponent = new KeyboardAccessibleActionButton(new SendMessageAction(), "Send");
    }
}
