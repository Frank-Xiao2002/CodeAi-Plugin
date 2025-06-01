package top.frankxxj.codeai.plugin.chat.model;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import top.frankxxj.codeai.plugin.settings.AppSettings;
import top.frankxxj.codeai.plugin.utils.BallonUtils;
import top.frankxxj.codeai.plugin.utils.HttpUtils;

import java.util.List;
import java.util.Objects;

@Service(Service.Level.APP)
public final class ModelManager {
    private static final Logger log = Logger.getInstance(ModelManager.class);
    private List<Model> models = List.of();

    public List<Model> getModels() {
        if (models.isEmpty()) {
            try {
                models = HttpUtils.webClient.get()
                        .uri(Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/model/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToFlux(Model.class)
                        .collectList().block();
            } catch (WebClientRequestException e) {
                BallonUtils.showWarnBallon("Failed to fetch models from server.<br>Adjust settings and reopen the project to retry connection.");
            }
            assert models != null;
            log.debug(models.size() + " models fetched: " + models);
        }
        return this.models;
    }

    public static ModelManager getInstance() {
        return ApplicationManager.getApplication().getService(ModelManager.class);
    }

}
