// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package top.frankxxj.codeai.plugin.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import lombok.Getter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import top.frankxxj.codeai.plugin.entity.dto.LoginRequestDTO;
import top.frankxxj.codeai.plugin.entity.dto.LoginResponseDTO;
import top.frankxxj.codeai.plugin.entity.dto.RegisterRequestDTO;
import top.frankxxj.codeai.plugin.i18n.MyBundle;
import top.frankxxj.codeai.plugin.utils.HttpUtils;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {
    @SuppressWarnings("WriteOnlyObject")
    @Getter
    private final AppSettings.State tmpState = new AppSettings.State();
    @Getter
    private String tmpToken = "";
    private static final int USER_FIELD_COLUMNS = 30;
    private final JPanel myMainPanel;
    private final JBTextField usernameTextField = new JBTextField(USER_FIELD_COLUMNS);
    private final JBPasswordField passwordField = new JBPasswordField();
    private final JBTextField serverUrl = new JBTextField();
    private final JButton loginButton = new JButton(MyBundle.message("settings.form.button.login"));
    private final JButton logoutButton = new JButton(MyBundle.message("settings.form.button.logout"));
    private final JButton registerButton = new JButton(MyBundle.message("settings.form.button.register"));
    private final ComboBox<String> defaultAnswerLanguage = new ComboBox<>(new String[]{"English", "中文"});
    private final List<JCheckBox> supportedFileTypesCheckBoxes = new ArrayList<>();

    public AppSettingsComponent(AppSettings.State state) {
        var loginLogoutPanel = new JPanel(new BorderLayout());
        loginLogoutPanel.add(loginButton, BorderLayout.WEST);
        loginLogoutPanel.add(logoutButton, BorderLayout.EAST);
        passwordField.setColumns(USER_FIELD_COLUMNS);
        JPanel fileTypePanels = new JPanel();
        fileTypePanels.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (String fileType : AppSettings.SUPPORTED_FILE_TYPES) {
            JBCheckBox box = new JBCheckBox(fileType);
            supportedFileTypesCheckBoxes.add(box);
            fileTypePanels.add(box);
        }
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(MyBundle.message("settings.form.username"), usernameTextField)
                .addLabeledComponent(MyBundle.message("settings.form.password"), passwordField)
                .addComponent(loginLogoutPanel)
                .addComponent(registerButton)
                .addSeparator()
                .addLabeledComponent(MyBundle.message("settings.form.serverUrl"), serverUrl)
                .addLabeledComponent(MyBundle.message("settings.form.languageButton"), defaultAnswerLanguage)
                .addLabeledComponent(MyBundle.message("settings.form.supportFileTypes"), fileTypePanels)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
        setComponentsValue(state);
        setComponentsEnabled();
        addListeners();
    }

    public void setComponentsValue(AppSettings.State state) {
        usernameTextField.setText(state.userName);
        passwordField.setText(state.userName.isEmpty() ? "" : "~~~~~~~~");
        serverUrl.setText(state.serverUrl);
        defaultAnswerLanguage.setSelectedItem(state.answerLanguage);
        supportedFileTypesCheckBoxes.forEach(jCheckBox -> {
            Boolean b = state.appliedFileTypes.get(jCheckBox.getText());
            jCheckBox.setSelected(b);
        });
    }

    private void setComponentsEnabled() {
        if (AppSettings.getInstance().isLoggedIn()) {
            usernameTextField.setEditable(false);
            passwordField.setEditable(false);
            logoutButton.setEnabled(true);
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
        } else {
            usernameTextField.setEditable(true);
            passwordField.setEditable(true);
            logoutButton.setEnabled(false);
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);
        }
    }

    private void addListeners() {
        loginButton.addActionListener(e -> {
            if (usernameTextField.getText().isBlank() || passwordField.getPassword().length == 0) {
                return;
            }
            LoginResponseDTO response;
            try {
                response = HttpUtils.restTemplate.postForEntity(
                        Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/login/token",
                        new LoginRequestDTO(usernameTextField.getText(), new String(passwordField.getPassword())),
                        LoginResponseDTO.class
                ).getBody();
                tmpState.userId = String.valueOf(Objects.requireNonNull(response).id());
                tmpState.userName = usernameTextField.getText();
                tmpState.roles = response.roles();
                tmpToken = response.token();
                passwordField.setEditable(false);
                usernameTextField.setEditable(false);
                Messages.showDialog("Login success", "CodeAi Plugin", new String[]{"OK"}, 0, null);
            } catch (HttpStatusCodeException ex) {
                Messages.showErrorDialog("Login failed - " + ex.getStatusCode(), "Login Fail");
            } catch (ResourceAccessException ex) {
                Messages.showErrorDialog("Login failed - " + "Incorrect Server Connection", "Login Fail");
            }
        });
        logoutButton.addActionListener(e -> {
            TokenUtils.clearToken();
            AppSettings.getInstance().clearUser();
            setComponentsValue(Objects.requireNonNull(AppSettings.getInstance().getState()));
            setComponentsEnabled();
            Messages.showDialog("Logout success", "CodeAi Plugin", new String[]{"OK"}, 0, null);
        });
        registerButton.addActionListener(e -> {
            if (usernameTextField.getText().isBlank() || passwordField.getPassword().length == 0) {
                return;
            }
            try {
                HttpUtils.restTemplate.postForEntity(
                        Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/user/create",
                        new RegisterRequestDTO(usernameTextField.getText().trim(), new String(passwordField.getPassword())),
                        String.class
                );
                String message = "Remember your username and password for future login.\n";
                message = message.concat("Username: " + usernameTextField.getText().trim() + "\n")
                        .concat("Password: " + new String(passwordField.getPassword()) + "\n");
                Messages.showDialog(message, "CodeAi Plugin", new String[]{"OK"}, 0, null);
                // Clear the fields after successful registration
                usernameTextField.setText("");
                passwordField.setText("");
            } catch (HttpStatusCodeException ex) {
                Messages.showErrorDialog("Register failed - " + ex.getStatusCode(), "Register Fail");
            } catch (ResourceAccessException ex) {
                Messages.showErrorDialog("Register failed - " + "Incorrect Server Connection", "Register Fail");
            }
        });
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return serverUrl;
    }

    public String getServerUrl() {
        return serverUrl.getText().trim();
    }

    public String getUsername() {
        return usernameTextField.getText().trim();
    }

    public String getDefaultAnswerLanguage() {
        return ((String) defaultAnswerLanguage.getSelectedItem());
    }

    public Map<String, Boolean> getAppliedFileTypes() {
        return supportedFileTypesCheckBoxes.stream()
                .collect(Collectors.toMap(JCheckBox::getText, JCheckBox::isSelected));
    }
}
