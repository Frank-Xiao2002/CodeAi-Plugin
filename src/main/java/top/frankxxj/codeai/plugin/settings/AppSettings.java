package top.frankxxj.codeai.plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.frankxxj.codeai.plugin.entity.AppUser;

@State(
        name = "top.frankxxj.codeai.plugin.settings.AppSettings",
        storages = @Storage("CodeAIPlugin.xml")
)
final class AppSettings implements PersistentStateComponent<AppSettings.State> {
    private State myState = new State();

    @Override
    public @Nullable AppSettings.State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    static class State {
        @NonNls
        public String userId = "John Smith";
        public boolean ideaStatus = false;
        //@OptionTag(converter = AppUserConverter.class)
        public AppUser user = null;
        public String serverUrl = "http://localhost:8080";
        //public Language responseLang = Language.ENGLISH;
    }

    static AppSettings getInstance() {
        return ApplicationManager.getApplication()
                .getService(AppSettings.class);
    }
}
