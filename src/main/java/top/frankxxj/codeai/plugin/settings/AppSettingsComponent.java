// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package top.frankxxj.codeai.plugin.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField myUserNameText = new JBTextField();
    private final JBCheckBox myIdeaUserStatus = new JBCheckBox("IntelliJ IDEA user");

    public AppSettingsComponent() {
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            SampleDialogWrapper dialog = new SampleDialogWrapper();
            if (dialog.showAndGet()) {
                // TODO: 5/11/2025 Authenticate user with backend
                String body = null;
                String token = "";
                //body = CommonUtils.getInstance().getObjectMapper().writeValueAsString(new LoginDTO(dialog.getUsername(), dialog.getPassword()));
                String host = "http://localhost:8080";
                //HttpRequests.post(host + "/api/login/token", "application/json")
                //        .connect()

                System.out.println("token = " + token);
            }
        });
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("User name:"), myUserNameText, 1, false)
                .addComponent(myIdeaUserStatus, 1)
                .addComponent(loginButton)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myUserNameText;
    }

    @NotNull
    public String getUserNameText() {
        return myUserNameText.getText();
    }

    public void setUserNameText(@NotNull String newText) {
        myUserNameText.setText(newText);
    }

    public boolean getIdeaUserStatus() {
        return myIdeaUserStatus.isSelected();
    }

    public void setIdeaUserStatus(boolean newStatus) {
        myIdeaUserStatus.setSelected(newStatus);
    }

}
