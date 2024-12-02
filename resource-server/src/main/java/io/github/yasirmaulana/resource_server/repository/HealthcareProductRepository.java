package io.github.yasirmaulana.resource_server.repository;

import io.github.yasirmaulana.resource_server.domain.HealthcareProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HealthcareProductRepository extends JpaRepository<HealthcareProduct, Long> {
    Page<HealthcareProduct> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
