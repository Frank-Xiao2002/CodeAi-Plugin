package top.frankxxj.codeai.plugin.utils;

import com.intellij.lang.Language;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.EditorTextFieldProviderImpl;
import top.frankxxj.codeai.plugin.chat.message.HtmlContentComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParser {
    public List<JComponent> parseMarkdownToComponents(String markdown, Project project) {
        List<JComponent> components = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?s)```([a-zA-Z0-9]*)\\n(.*?)```");
        Matcher matcher = pattern.matcher(markdown);
        int lastEnd = 0;
        while (matcher.find()) {
            // Add non-code part
            if (matcher.start() > lastEnd) {
                String htmlPart = markdown.substring(lastEnd, matcher.start());
                HtmlContentComponent htmlComponent = new HtmlContentComponent();
                htmlComponent.setText(htmlPart); // You may want to convert markdown to HTML here
                components.add(htmlComponent);
            }
            // Add code part
            String language = matcher.group(1); // e.g., "java"
            String code = matcher.group(2);
            EditorTextField codeComponent = ApplicationManager.getApplication().getService(EditorTextFieldProviderImpl.class)
                    .getEditorField(Language.findLanguageByID(language), project, List.of());

            // Replace with actual usage
            components.add(codeComponent);
            lastEnd = matcher.end();
        }
        // Add remaining non-code part
        if (lastEnd < markdown.length()) {
            String htmlPart = markdown.substring(lastEnd);
            HtmlContentComponent htmlComponent = new HtmlContentComponent();
            htmlComponent.setText(htmlPart);
            components.add(htmlComponent);
        }
        return components;
    }
}
