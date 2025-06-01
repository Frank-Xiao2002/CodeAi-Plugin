package top.frankxxj.codeai.plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.frankxxj.codeai.plugin.utils.TokenUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@State(
        name = "top.frankxxj.codeai.plugin.settings.AppSettings",
        storages = {@Storage("CodeAIPlugin.xml")}
)
public final class AppSettings implements PersistentStateComponent<AppSettings.State> {
    public static final List<String> SUPPORTED_FILE_TYPES = List.of(
            ".java", ".py", ".html", ".css", ".sql", ".c", ".cpp"
    );
    private State myState = new State();

    @Override
    public @Nullable AppSettings.State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    @ToString()
    public static class State {
        public String userId;
        public String userName;
        public String roles;
        public String serverUrl;
        public String answerLanguage;
        public Map<String, Boolean> appliedFileTypes;

        public State() {
            this.userName = "";
            this.userId = "";
            this.roles = "";
            this.answerLanguage = "English";
            this.appliedFileTypes = new HashMap<>();
            SUPPORTED_FILE_TYPES.forEach(s -> this.appliedFileTypes.put(s, true));
            this.serverUrl = "http://localhost:8080";
        }
    }

    public static AppSettings getInstance() {
        return ApplicationManager.getApplication()
                .getService(AppSettings.class);
    }

    public boolean isLoggedIn() {
        return !myState.userName.isEmpty() &&
                TokenUtils.retrieveToken() != null &&
                !TokenUtils.retrieveToken().isEmpty();
    }

    public void clearUser() {
        myState.userId = "";
        myState.userName = "";
        myState.roles = "";
    }

}
