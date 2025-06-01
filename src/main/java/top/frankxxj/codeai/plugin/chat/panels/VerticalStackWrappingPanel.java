package top.frankxxj.codeai.plugin.chat.panels;

import com.intellij.openapi.project.Project;
import top.frankxxj.codeai.plugin.chat.message.CodeAiMessageComponent;
import top.frankxxj.codeai.plugin.chat.message.MessageComponent;
import top.frankxxj.codeai.plugin.chat.message.UserMessageComponent;
import top.frankxxj.codeai.plugin.entity.ChatSession;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;
import top.frankxxj.codeai.plugin.settings.AppSettings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VerticalStackWrappingPanel extends JPanel {
    public static final String NAME = "messagesPanel";
    ArrayList<MessageComponent> messages;

    public VerticalStackWrappingPanel(Project project) {
        project.getService(ProjectComponentManager.class).registerComponent(NAME, this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void resetContent(Project project) {
        removeAll();
        var username = Objects.requireNonNull(AppSettings.getInstance().getState()).userName;
        messages = new ArrayList<>();
        var conversationService = project.getService(ProjectConversationService.class);
        List<ChatSession> sessions = conversationService.getSessions(conversationService.getCurrentConversationId());

        for (ChatSession session : sessions) {
            MessageComponent messageComponent;
            switch (session.getMessageType()) {
                case USER -> messageComponent = new UserMessageComponent(session, username);
                case ASSISTANT -> messageComponent = new CodeAiMessageComponent(session);
                default -> messageComponent = new MessageComponent(session, "CodeAi");
            }
            messages.add(messageComponent);
            add(messageComponent);
        }

    }
}
