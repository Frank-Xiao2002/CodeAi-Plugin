package top.frankxxj.codeai.plugin.chat.session.component;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import top.frankxxj.codeai.plugin.chat.panels.ActionButtonsPanel;
import top.frankxxj.codeai.plugin.chat.window.actions.BackToCurrentSessionAction;
import top.frankxxj.codeai.plugin.project.ProjectComponentManager;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAllConversationComponent extends JPanel {
    private static final Logger log = Logger.getInstance(ViewAllConversationComponent.class);
    public static final String NAME = "ViewAllConversationComponent";
    private final AllConversationComponent view;

    public ViewAllConversationComponent(Project project) {
        project.getService(ProjectComponentManager.class).registerComponent(NAME, this);
        setLayout(new BorderLayout(0, 0));
        setMinimumSize(new Dimension(133, 57));
        setPreferredSize(new Dimension(478, 180));
        Presentation backButtonPresentation = new Presentation("Back");
        backButtonPresentation.setIcon(AllIcons.Actions.Back);
        backButtonPresentation.setDescription("Back to current session");
        ActionButtonWithText back = new ActionButtonWithText(new BackToCurrentSessionAction(),
                backButtonPresentation, "ChatSessionToolBar", new Dimension(57, 17));
        var toolbar = new ChatSessionToolBarComponent(new ActionButtonsPanel(), back);
        add(toolbar, BorderLayout.NORTH);
        var centerPane = new JBScrollPane();
        add(centerPane, BorderLayout.CENTER);
        view = new AllConversationComponent();
        centerPane.setViewportView(view);
    }

    public void refreshConversationList(Project project) {
        log.debug("Refreshing conversation list...");
        view.removeAll();
        var conversationService = project.getService(ProjectConversationService.class);
        List<String> allConversationIds = conversationService.getAllConversationIds();
        int gridy = 0;

        for (String cid : allConversationIds) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = gridy++;
            constraints.weightx = 1;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.anchor = GridBagConstraints.NORTHWEST;

            view.add(new ChatSessionOption(project, cid),
                    constraints);
        }
    }


}
