package top.frankxxj.codeai.plugin.utils;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Singleton class to provide a RestTemplate instance.
 */
public class CommonUtils {

    public static final RestTemplate restTemplate;
    public static final WebClient webClient;

    static {
        restTemplate = new RestTemplate();
        webClient = WebClient.create();
        System.out.println("RestTemplate has following messageConverters: " + restTemplate.getMessageConverters());
    }


}
