package io.github.yasirmaulana.resource_server.web;

import io.github.yasirmaulana.resource_server.service.SatuSehatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RestController
@RequestMapping("/api")
public class SatuSehatResource {

    private final SatuSehatService satuSehatService;
    private final RestClient restClient;

    public SatuSehatResource(SatuSehatService satuSehatService, RestClient restClient) {
        this.satuSehatService = satuSehatService;
        this.restClient = restClient;
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<String> getPatient(@PathVariable String id) {
        return ResponseEntity.ok(satuSehatService.getPatientData(id));
    }

    @GetMapping("/patient2/{id}")
    public String fetchPatient() {
        return restClient.get()
                .uri("https://api-satusehat-stg.dto.kemkes.go.id/fhir-r4/v1/Patient/P02478375538")
                .attributes(clientRegistrationId("satusehat"))
                .retrieve()
                .body(String.class);
    }
}