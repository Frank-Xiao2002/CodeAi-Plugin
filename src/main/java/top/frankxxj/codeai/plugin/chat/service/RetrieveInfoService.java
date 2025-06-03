package top.frankxxj.codeai.plugin.chat.service;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.frankxxj.codeai.plugin.entity.EditorFile;
import top.frankxxj.codeai.plugin.entity.dto.ChatDTO;
import top.frankxxj.codeai.plugin.project.ProjectConversationService;
import top.frankxxj.codeai.plugin.settings.AppSettings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Setter
@Service(Service.Level.PROJECT)
public final class RetrieveInfoService {
    private JTextArea promptTextArea;
    private ComboBox<String> modelComboBox;

    /**
     * Retrieves the selected files from the editor.
     *
     * @param e The action event containing the context of the action.
     * @return A list of EditorFile objects representing the selected files.
     */
    private List<EditorFile> getSelectedFiles(AnActionEvent e) {
        Project project = e.getProject();
        var result = new ArrayList<EditorFile>();
        if (project == null) return result;

        VirtualFile[] files = FileEditorManager.getInstance(project).getSelectedFiles();
        if (files.length == 0) return result;

        return Arrays.stream(files).filter(virtualFile -> {
            // Check if the file is a valid text file and is not excluded by the settings
            String fileType = virtualFile.getExtension();
            if (fileType == null || !AppSettings.SUPPORTED_FILE_TYPES.contains("." + fileType)) {
                return true;
            }
            // Check if the file is not excluded by the user settings
            return AppSettings.getInstance().getState().appliedFileTypes.getOrDefault("." + fileType, true);
        }).map(virtualFile -> {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (psiFile == null) return null;

            Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
            if (document == null) return null;

            String filename = virtualFile.getName();
            String content = document.getText();
            return new EditorFile(filename, content);
        }).toList();
    }

    public String getPrompt() {
        return promptTextArea.getText();
    }

    public String getModel() {
        return modelComboBox.getItem();
    }

    public void clearPrompt() {
        if (promptTextArea != null) {
            promptTextArea.setText("");
        }
    }

    public String getSelectedSnippet(AnActionEvent e) {
        var editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return null;
        var selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        return selectedText != null && !selectedText.isBlank() ? selectedText : null;
    }

    public ChatDTO buildRequestBody(AnActionEvent e, String prompt) {
        String selectedSnippet = getSelectedSnippet(e);
        var selectedFiles = getSelectedFiles(e);
        String conversationId = e.getProject().getService(ProjectConversationService.class).getCurrentConversationId();
        String answerLanguage = AppSettings.getInstance().getState().answerLanguage;
        if (answerLanguage.equals("中文")) {
            answerLanguage = "Chinese";
        }
        return new ChatDTO(prompt, conversationId, selectedSnippet, answerLanguage, selectedFiles);
    }
}
