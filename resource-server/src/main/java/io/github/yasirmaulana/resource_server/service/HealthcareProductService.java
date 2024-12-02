package io.github.yasirmaulana.resource_server.service;

import io.github.yasirmaulana.resource_server.domain.HealthcareProduct;
import io.github.yasirmaulana.resource_server.dto.HealthcareProductRequestDTO;
import io.github.yasirmaulana.resource_server.dto.HealthcareProductResponseDTO;
import io.github.yasirmaulana.resource_server.dto.ResultPageResponseDTO;

import java.util.List;

public interface HealthcareProductService {
    void createHealthcareProducts(List<HealthcareProductRequestDTO> dtos);
    void updateHealthcareProduct(Long productId, HealthcareProductRequestDTO dto);
    void deleteHealthcareProduct(Long id);
    HealthcareProductResponseDTO getHealtcareProductById(Long id);
    ResultPageResponseDTO<HealthcareProductResponseDTO> getHealthcareProductPage(Integer pages, Integer limit,
                                                                                 String sortBy, String direction, String productName);
}
