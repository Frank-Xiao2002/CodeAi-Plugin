package top.frankxxj.codeai.plugin.project;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import top.frankxxj.codeai.plugin.chat.panels.VerticalStackWrappingPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@Service(Service.Level.PROJECT)
public final class ProjectComponentManager {
    private Map<String, JComponent> componentMap;

    public ProjectComponentManager() {
        componentMap = new HashMap<>();
    }

    public void registerComponent(String key, JComponent component) {
        if (key == null || component == null) {
            throw new IllegalArgumentException("Key and component must not be null");
        }
        componentMap.put(key, component);
    }

    public JComponent getComponent(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }
        return componentMap.get(key);
    }

    public void updateMessageAndConversation(Project project) {
        //update message panel
        JComponent messagePanel = componentMap.get(VerticalStackWrappingPanel.NAME);
        ((VerticalStackWrappingPanel) messagePanel).resetContent(project);
        //update conversation panel


    }

    public void resetMessage(Project project) {
        JComponent messagePanel = componentMap.get(VerticalStackWrappingPanel.NAME);
        ((VerticalStackWrappingPanel) messagePanel).resetContent(project);
    }

}
