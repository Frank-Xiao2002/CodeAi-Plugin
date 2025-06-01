package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.project.Project;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.JBColor;
import com.intellij.ui.RoundedLineBorder;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import top.frankxxj.codeai.plugin.chat.service.RetrieveInfoService;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CodeAiChatInputPanel extends JPanel {
    private JTextArea textInputArea = new CodeAiChatInputTextArea();
    private JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    private JPanel bottomPanel = new CodeAiChatInputToolbarPanel();
    private ModelPickPanel modelPickPanel = new ModelPickPanel();
    private JPanel buttonPanel = new SendStopActionButtonPanel();

    public CodeAiChatInputPanel(Project project) {
        var retrieveInfoService = project.getService(RetrieveInfoService.class);
        retrieveInfoService.setPromptTextArea(textInputArea);
        retrieveInfoService.setModelComboBox(modelPickPanel);

        setBorder(new CompoundBorder(JBUI.Borders.empty(12),
                new CompoundBorder(JBUI.Borders.empty(1),
                        new RoundedLineBorder(new JBColor(new Color(202, 204, 212), new Color(79, 81, 86))))
        ));
        setLayout(new BorderLayout(0, 0));
        JPanel innerPanel = new JPanel();
        innerPanel.setAlignmentX(CENTER_ALIGNMENT);
        innerPanel.setAlignmentY(CENTER_ALIGNMENT);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        add(innerPanel, BorderLayout.CENTER);
        innerPanel.add(new CodeAiChatInputPanelWrapper(textInputArea));
        innerPanel.add(bottomPanel);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 1.0;
        constraints.ipadx = 9;
        bottomPanel.add(rightPanel, constraints);
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(131, 44));
        rightPanel.add(modelPickPanel);
        rightPanel.add(buttonPanel);
        modelPickPanel.setOpaque(false);
        setupListeners();
        buttonPanel.setEnabled(false);
    }

    private void setupListeners() {
        textInputArea.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                buttonPanel.setEnabled(!textInputArea.getText().isEmpty() && modelPickPanel.getItemCount() > 0);
            }
        });
        modelPickPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Calculate new preferred width for rightPanel
                int newWidth = modelPickPanel.getPreferredSize().width +
                        buttonPanel.getPreferredSize().width + 20;

                // Set new preferred size for rightPanel
                rightPanel.setPreferredSize(new Dimension(newWidth, 44));

                // Force layout update
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });
        textInputArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "sendAction");
        textInputArea.getActionMap().put("sendAction", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                for (Component c : buttonPanel.getComponents()) {
                    if (c.isEnabled() && c instanceof ActionButton) {
                        ((ActionButton) c).click();
                        break;
                    }
                }
            }
        });
        textInputArea.getInputMap().put(KeyStroke.getKeyStroke("shift ENTER"), "insert-break");
    }

    public String getInputText() {
        return textInputArea.getText();
    }
}