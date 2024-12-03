package io.github.yasirmaulana.client_application.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;


@RestController
@RequestMapping("/api/products")
public class HealthcareProductController {

    private final RestClient restClient;

    @Value("${app.product.resource-server-url}")
    private String resourceServerUrl;

    @Value("${app.product.client-registration-id}")
    private String clientRegistrationId;

    public HealthcareProductController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping
    public String fetchProducts() {
        return restClient.get()
                .uri(resourceServerUrl)
                .attributes(clientRegistrationId(clientRegistrationId))
                .retrieve()
                .body(String.class);
    }
}
