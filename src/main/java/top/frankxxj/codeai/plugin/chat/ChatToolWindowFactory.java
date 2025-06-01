package top.frankxxj.codeai.plugin.chat;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import top.frankxxj.codeai.plugin.chat.session.component.ChatSessionTransitionalComponent;

import javax.swing.*;

@Getter
public final class ChatToolWindowFactory implements ToolWindowFactory, DumbAware {
    private JPanel contentPanel;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.contentPanel = new ChatSessionTransitionalComponent(project);
        Content content = ContentFactory.getInstance().createContent(contentPanel, "Chat", false);
        toolWindow.getContentManager().addContent(content);
    }


}