package top.frankxxj.codeai.plugin.chat.input;

import top.frankxxj.codeai.plugin.chat.model.ModelManager;

import java.awt.*;

public class ModelPickPanel extends ModelPickComboBox {
    public ModelPickPanel() {
        setMinimumSize(new Dimension(94, 34));
        setPreferredSize(new Dimension(94, 34));
        setEditable(false);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        ModelManager modelManager = ModelManager.getInstance();
        modelManager.getModels().forEach(model -> {
            addItem(model.getModelName() + (model.getAuthorities().contains("USER") ? "" : " (pro)"));
        });
    }
}
