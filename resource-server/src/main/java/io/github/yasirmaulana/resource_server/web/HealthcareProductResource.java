package io.github.yasirmaulana.resource_server.web;

import io.github.yasirmaulana.resource_server.dto.HealthcareProductRequestDTO;
import io.github.yasirmaulana.resource_server.dto.HealthcareProductResponseDTO;
import io.github.yasirmaulana.resource_server.dto.ResultPageResponseDTO;
import io.github.yasirmaulana.resource_server.service.HealthcareProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/healthcare-product")
public class HealthcareProductResource {

    private final HealthcareProductService healthcareProductService;

    public HealthcareProductResource(HealthcareProductService healthcareProductService) {
        this.healthcareProductService = healthcareProductService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid List<HealthcareProductRequestDTO> dtos) {
        healthcareProductService.createHealthcareProducts(dtos);
        return ResponseEntity.created(URI.create("/v1/healthcare-product")).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody @Valid HealthcareProductRequestDTO dto) {
        healthcareProductService.updateHealthcareProduct(productId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        healthcareProductService.deleteHealthcareProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<HealthcareProductResponseDTO> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok().body(healthcareProductService.getHealtcareProductById(productId));
    }

    @GetMapping
    public ResponseEntity<ResultPageResponseDTO<HealthcareProductResponseDTO>> getProductPages(
            @RequestParam(name = "page", required = true, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
            @RequestParam(name = "productName", required = false) String productName) {
        return ResponseEntity.ok().body(healthcareProductService.getHealthcareProductPage(pages, limit, sortBy, direction, productName));
    }
}