package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.openapi.actionSystem.Presentation;
import lombok.Getter;
import top.frankxxj.codeai.plugin.icons.CodeAiIcons;
import top.frankxxj.codeai.plugin.ui.component.KeyboardAccessibleActionButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@Getter
public class SendStopActionButtonPanel extends JPanel {
    private static final String SEND = "SEND";
    private static final String STOP = "STOP";
    private final CardLayout cardLayout = new CardLayout(0, 0);
    private KeyboardAccessibleActionButton sendActionComponent;
    private KeyboardAccessibleActionButton stopActionComponent;

    public SendStopActionButtonPanel() {
        setLayout(cardLayout);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        sendActionComponent = new KeyboardAccessibleActionButton(new SendMessageAction(),
                new Presentation("Send"), "ChatInput1", new Dimension(26, 22));
        //stopActionComponent = new KeyboardAccessibleActionButton(new StopAction(),
        //        new Presentation("Stop"), "ChatInput2", new Dimension(26, 22));
        sendActionComponent.getPresentation().setIcon(CodeAiIcons.Send);
        add(sendActionComponent, SEND);
        //add(stopActionComponent, STOP);
        cardLayout.show(this, SEND);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        Arrays.stream(getComponents()).filter(Component::isVisible)
                .findFirst().get().setEnabled(enabled);
    }
}
