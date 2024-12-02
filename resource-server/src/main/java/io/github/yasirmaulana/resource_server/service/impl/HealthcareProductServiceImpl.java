package io.github.yasirmaulana.resource_server.service.impl;

import io.github.yasirmaulana.resource_server.domain.HealthcareProduct;
import io.github.yasirmaulana.resource_server.dto.HealthcareProductRequestDTO;
import io.github.yasirmaulana.resource_server.dto.HealthcareProductResponseDTO;
import io.github.yasirmaulana.resource_server.dto.ResultPageResponseDTO;
import io.github.yasirmaulana.resource_server.exception.NoFoundException;
import io.github.yasirmaulana.resource_server.repository.HealthcareProductRepository;
import io.github.yasirmaulana.resource_server.service.HealthcareProductService;
import io.github.yasirmaulana.resource_server.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class HealthcareProductServiceImpl implements HealthcareProductService {

    private final HealthcareProductRepository healthcareProductRepository;

    @Override
    public void createHealthcareProducts(List<HealthcareProductRequestDTO> dtos) {
        List<HealthcareProduct> healthcareProducts = dtos.stream()
                .map(p -> {
                    return HealthcareProduct.builder()
                            .name(p.getName())
                            .sku(p.getSku())
                            .imageUrl(p.getImageUrl())
                            .price(p.getPrice())
                            .description(p.getDescription())
                            .category(p.getCategory())
                            .build();
                }).toList();

        healthcareProductRepository.saveAll(healthcareProducts);
    }

    @Override
    public void updateHealthcareProduct(Long productId, HealthcareProductRequestDTO dto) {
        HealthcareProduct existingProduct = healthcareProductRepository.findById(productId)
                .orElseThrow(() -> new NoFoundException("HealtcareProduct ID invalid or ID not found"));

        HealthcareProduct.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .imageUrl(dto.getImageUrl())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .build();

        healthcareProductRepository.save(existingProduct);
    }

    @Override
    public void deleteHealthcareProduct(Long id) {
        healthcareProductRepository.deleteById(id);
    }

    @Override
    public HealthcareProductResponseDTO getHealtcareProductById(Long productId) {
        HealthcareProduct healthcareProduct = healthcareProductRepository.findById(productId)
                .orElseThrow(() -> new NoFoundException("invalid.product.id"));

        return HealthcareProductResponseDTO.builder()
                .id(healthcareProduct.getId())
                .name(healthcareProduct.getName())
                .sku(healthcareProduct.getSku())
                .imageUrl(healthcareProduct.getImageUrl())
                .price(healthcareProduct.getPrice())
                .description(healthcareProduct.getDescription())
                .category(healthcareProduct.getCategory())
                .build();
    }

    @Override
    public ResultPageResponseDTO<HealthcareProductResponseDTO> getHealthcareProductPage(Integer pages, Integer limit,
                                                                                            String sortBy, String direction, String productName) {
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        productName = StringUtils.isBlank(productName)? "%" : productName+"%";
        Page<HealthcareProduct> pageResult = healthcareProductRepository.findByNameLikeIgnoreCase(productName,pageable);
        List<HealthcareProductResponseDTO> dtos = pageResult.stream()
                .map(p -> {
                    return HealthcareProductResponseDTO.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .sku(p.getSku())
                            .imageUrl(p.getImageUrl())
                            .price(p.getPrice())
                            .description(p.getDescription())
                            .category(p.getCategory())
                            .build();
                }).toList();

        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

}
