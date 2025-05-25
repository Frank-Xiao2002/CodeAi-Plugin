package top.frankxxj.codeai.plugin.chat.message.codeblock;

import javax.swing.*;
import java.awt.*;

public class CodeBlockContainer extends JPanel {
    private CodeBlockPanel codeBlockPanel;

    public CodeBlockContainer() {
        this.codeBlockPanel = new CodeBlockPanel(this);
        setLayout(new BorderLayout(0, 0));
    }
}
