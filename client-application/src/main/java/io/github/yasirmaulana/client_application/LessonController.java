package io.github.yasirmaulana.client_application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;


@RestController
public class LessonController {

    private final RestClient restClient;

    public LessonController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/lessons")
    public String fetchLessons() {
        return restClient.get()
                .uri("http://localhost:8081/lessons")
                .attributes(clientRegistrationId("healthcare-client"))
                .retrieve()
                .body(String.class);
    }
}
