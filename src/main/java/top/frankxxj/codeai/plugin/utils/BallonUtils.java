package top.frankxxj.codeai.plugin.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;

public class BallonUtils {
    public static final String GROUP_ID = "CodeAi";

    public static void showInfoBallon(String content) {
        Notifications.Bus.notify(new Notification(GROUP_ID,
                GROUP_ID, content,
                NotificationType.INFORMATION
        ));
    }

    public static void showWarnBallon(String content) {
        Notifications.Bus.notify(new Notification(GROUP_ID,
                GROUP_ID, content,
                NotificationType.WARNING
        ));
    }

    public static void showWarnBallonWithAction(String content, AnAction action) {
        Notifications.Bus.notify(new Notification(GROUP_ID,
                GROUP_ID,
                content,
                NotificationType.WARNING
        ).addAction(action));
    }

}
