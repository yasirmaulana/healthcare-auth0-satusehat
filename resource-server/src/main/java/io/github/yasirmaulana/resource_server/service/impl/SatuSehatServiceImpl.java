package io.github.yasirmaulana.resource_server.service.impl;

import io.github.yasirmaulana.resource_server.exception.NoFoundException;
import io.github.yasirmaulana.resource_server.service.SatuSehatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class SatuSehatServiceImpl implements SatuSehatService {
    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Autowired
    public SatuSehatServiceImpl(RestTemplateBuilder restTemplateBuilder,
                                OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restTemplate = restTemplateBuilder.build();
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public String getPatientData(String patientId) {
        // Dapatkan token akses
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("satusehat")
                .principal("client")
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient == null) {
            throw new IllegalStateException("Token tidak dapat diperoleh.");
        }

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // Buat request ke SatuSehat API
        String url = "https://api-satusehat-stg.dto.kemkes.go.id/fhir-r4/v1/Patient/" + patientId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new NoFoundException("Error saat mengambil data pasien: " + ex.getResponseBodyAsString());
        }
    }
}
