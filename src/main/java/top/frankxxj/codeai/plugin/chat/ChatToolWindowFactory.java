package top.frankxxj.codeai.plugin.chat;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.frankxxj.codeai.plugin.chat.message.MessageComponent;
import top.frankxxj.codeai.plugin.icons.CodeAiIcons;

import javax.swing.*;
import java.awt.*;
import java.util.List;

final class ChatToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ChatToolWindowContent toolWindowContent = new ChatToolWindowContent(toolWindow);
        Content content = ContentFactory.getInstance().createContent(toolWindowContent.getContentPanel(), "Chat", false);
        toolWindow.getContentManager().addContent(content);
    }


    private static class ChatToolWindowContent {
        private final JPanel contentPanel = new JPanel();
        private final JPanel inputPanel = new InputPanel();
        private List<MessageComponent> messages;

        public ChatToolWindowContent(@NotNull ToolWindow toolWindow) {
            // TODO: 5/24/2025 show history
        }

        public @Nullable JPanel getContentPanel() {
            return this.contentPanel;
        }
    }

}

class InputPanel extends JPanel {
    private final JScrollPane inputPane = new JBScrollPane();
    private final JPanel down = new JPanel();
    private final JButton sendButton;
    private final JPanel modelPickPanel = new ModelPickPanel();

    public InputPanel() {
        inputPane.setToolTipText("Ask CodeAi anything about code here.");
        sendButton = new JButton(CodeAiIcons.Send);
        sendButton.addActionListener(e -> {
            // TODO: 5/24/2025 add send action listener
            System.out.println("Send button clicked");

        });
        setLayout(new VerticalLayout(0));
        HorizontalLayout downLayout = new HorizontalLayout(5);
        downLayout.addLayoutComponent(modelPickPanel, HorizontalLayout.Group.RIGHT);
        downLayout.addLayoutComponent(sendButton, HorizontalLayout.Group.RIGHT);
        down.setLayout(downLayout);
        down.setBackground(new JBColor(new Color(223, 225, 229), new Color(57, 59, 64)));
        add(inputPane);
        add(down);
    }
}

class ModelPickPanel extends JPanel {
    public ModelPickPanel() {
    }
}