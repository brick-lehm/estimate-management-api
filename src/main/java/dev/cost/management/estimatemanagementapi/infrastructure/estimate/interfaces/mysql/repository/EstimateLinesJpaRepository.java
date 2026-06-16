package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository;

import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLinesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateLinesJpaRepository extends JpaRepository<EstimateLinesEntity, String> {
}
