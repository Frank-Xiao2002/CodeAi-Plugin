package top.frankxxj.codeai.plugin.settings;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.util.ui.FormBuilder;
import com.jetbrains.cef.remote.thrift.annotation.Nullable;

import javax.swing.*;
import java.awt.*;

public class SampleDialogWrapper extends DialogWrapper {
    private final JTextField username = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public SampleDialogWrapper() {
        super(true); // use current window as parent
        setTitle("Login");
        setSizes();
        init();
    }

    private void setSizes() {
        Dimension size = new Dimension(200, 30);
        username.setPreferredSize(size);
        passwordField.setPreferredSize(size);
    }


    @Override
    protected @org.jetbrains.annotations.Nullable ValidationInfo doValidate() {
        if (username.getText().isBlank() || String.valueOf(passwordField.getPassword()).isBlank())
            return new ValidationInfo("Invalid username or password");
        else return null;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Username: ", username)
                .addLabeledComponent("Password: ", passwordField)
                .getPanel();
    }

    public String getUsername() {
        return username.getText().trim();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }
}