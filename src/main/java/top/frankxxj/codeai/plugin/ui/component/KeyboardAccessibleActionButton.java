package top.frankxxj.codeai.plugin.ui.component;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class KeyboardAccessibleActionButton extends ActionButton {
    public KeyboardAccessibleActionButton(@NotNull AnAction action, @Nullable Presentation presentation, @NotNull String place, @NotNull Dimension minimumSize) {
        super(action, presentation, place, minimumSize);
        setBorder(JBUI.Borders.emptyRight(4));
        setOpaque(false);
    }

}
