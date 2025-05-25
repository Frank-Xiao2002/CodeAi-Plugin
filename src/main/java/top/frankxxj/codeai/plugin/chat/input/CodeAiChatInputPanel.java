package top.frankxxj.codeai.plugin.chat.input;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CodeAiChatInputPanel extends JPanel {
    private static JTextArea textInputArea;
    private static JPanel rightPanel;
    private static JPanel bottomPanel;
    private static JPanel modelPickPanel;
    private static JPanel buttonPanel;

    static class CodeAiChatInputPanelWrapper extends JPanel {
        private JScrollPane inputPane;

        CodeAiChatInputPanelWrapper() {
            inputPane = new JBScrollPane();
            inputPane.setToolTipText("Ask CodeAi anything about code here.");
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            textInputArea = new JBTextArea();
            inputPane.add(textInputArea);
            textInputArea.getDocument().addDocumentListener(new DocumentListener() {
                // TODO: 5/25/2025 add document listener to disable and enable send button
                @Override
                public void insertUpdate(DocumentEvent e) {

                }

                @Override
                public void removeUpdate(DocumentEvent e) {

                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }

            });
            add(inputPane);
        }

    }

    static class CodeAiChatInputToolBarPanel extends JPanel {
        public CodeAiChatInputToolBarPanel() {
            setLayout(new GridBagLayout());

        }

    }

    static class ModelPickPanel extends ComboBox<String> {
        public ModelPickPanel() {

        }
    }

    static class SendStopActionButtonPanel extends JPanel {
        private JComponent sendActionButton;
        private JComponent stopActionButton;

        public SendStopActionButtonPanel() {

        }
    }
}