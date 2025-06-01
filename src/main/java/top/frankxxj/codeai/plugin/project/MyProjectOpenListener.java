package top.frankxxj.codeai.plugin.project;

import com.intellij.notification.NotificationAction;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.frankxxj.codeai.plugin.chat.model.ModelManager;
import top.frankxxj.codeai.plugin.settings.AppSettings;
import top.frankxxj.codeai.plugin.settings.AppSettingsConfigurable;
import top.frankxxj.codeai.plugin.utils.BallonUtils;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import java.time.LocalDateTime;

public class MyProjectOpenListener implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        ModelManager.getInstance().getModels();
        if (!AppSettings.getInstance().isLoggedIn()) {
            //no login info
            var message = "User is not logged in. Please log in to access the features of CodeAI." +
                    "You can log in from the settings.";
            BallonUtils.showWarnBallonWithAction(message, NotificationAction.create("Open settings",
                    (event, notification) -> ShowSettingsUtil.getInstance()
                            .showSettingsDialog(project, AppSettingsConfigurable.class)));
        } else if (!TokenUtils.validateToken()) {
            //token expired
            var message = "Login info expired. Please re-login to access the features of CodeAI." +
                    "You can log in from the settings.";
            BallonUtils.showWarnBallonWithAction(message, NotificationAction.create("Open settings",
                    (event, notification) -> ShowSettingsUtil.getInstance()
                            .showSettingsDialog(project, AppSettingsConfigurable.class)));
        } else {
            //logged in with valid token
            project.getService(ProjectConversationService.class).loadHistory(project);

        }
        System.out.println("Project opened: " + project.getName() + " at " + LocalDateTime.now());
        return null;
    }
}
