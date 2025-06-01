// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package top.frankxxj.codeai.plugin.settings;

import com.intellij.openapi.options.Configurable;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import javax.swing.*;
import java.util.Objects;

/**
 * Provides controller functionality for application settings.
 */
@NoArgsConstructor
public final class AppSettingsConfigurable implements Configurable {
    public static final String DISPLAY_NAME = "CodeAi Settings";
    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because
    // this implementation is registered as an applicationConfigurable

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent(AppSettings.getInstance().getState());
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettings.State state =
                Objects.requireNonNull(AppSettings.getInstance().getState());
        return !mySettingsComponent.getServerUrl().equals(state.serverUrl) ||
                !mySettingsComponent.getUsername().equals(state.userName) ||
                !mySettingsComponent.getDefaultAnswerLanguage().equals(state.answerLanguage) ||
                !mySettingsComponent.getAppliedFileTypes().equals(state.appliedFileTypes);

    }

    @Override
    public void apply() {
        AppSettings.State state =
                Objects.requireNonNull(AppSettings.getInstance().getState());
        state.serverUrl = mySettingsComponent.getServerUrl();
        state.answerLanguage = mySettingsComponent.getDefaultAnswerLanguage();
        state.appliedFileTypes = mySettingsComponent.getAppliedFileTypes();
        if (!mySettingsComponent.getTmpToken().isEmpty()) {
            AppSettings.State tmpState = mySettingsComponent.getTmpState();
            state.userName = mySettingsComponent.getUsername();
            state.roles = tmpState.roles;
            state.userId = tmpState.userId;
            TokenUtils.storeToken(mySettingsComponent.getTmpToken());
        }
    }

    @Override
    public void reset() {
        AppSettings.State state =
                Objects.requireNonNull(AppSettings.getInstance().getState());
        mySettingsComponent.setComponentsValue(state);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
