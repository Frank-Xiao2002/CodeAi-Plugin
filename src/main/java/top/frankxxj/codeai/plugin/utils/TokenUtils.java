package top.frankxxj.codeai.plugin.utils;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import top.frankxxj.codeai.plugin.settings.AppSettings;

import java.util.Objects;

public class TokenUtils {
    public static final String TOKEN_KEY = "token";
    private static String cacheToken = "";

    private static CredentialAttributes createCredentialAttributes() {
        return new CredentialAttributes(
                CredentialAttributesKt.generateServiceName("CodeAi", TokenUtils.TOKEN_KEY)
        );
    }

    public static void storeToken(String token) {
        new Thread(() -> {
            CredentialAttributes attributes = createCredentialAttributes();
            Credentials credentials = new Credentials("CodeAi-user", token);
            PasswordSafe.getInstance().set(attributes, credentials);
        }).start();
        cacheToken = token;
    }

    public static String retrieveToken() {
        if (cacheToken.isEmpty()) {
            CredentialAttributes attributes = createCredentialAttributes();
            cacheToken = PasswordSafe.getInstance().getPassword(attributes);
        }
        return cacheToken;
    }

    public static void clearToken() {
        new Thread(() -> {
            CredentialAttributes attributes = createCredentialAttributes();
            PasswordSafe.getInstance().set(attributes, null);
        }).start();
        cacheToken = "";
    }

    public static boolean validateToken() {
        var headers = new HttpHeaders();
        headers.setBearerAuth(TokenUtils.retrieveToken());
        var requestEntity = RequestEntity
                .post(Objects.requireNonNull(AppSettings.getInstance().getState()).serverUrl + "/api/token/test")
                .headers(headers)
                .build();
        try {
            HttpUtils.restTemplate.exchange(requestEntity, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        } catch (ResourceAccessException e) {
            return true;
        }
    }
}
