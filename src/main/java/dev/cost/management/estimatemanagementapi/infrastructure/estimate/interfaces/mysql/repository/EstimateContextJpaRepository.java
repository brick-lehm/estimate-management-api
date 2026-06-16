package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository;

import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateContextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateContextJpaRepository extends JpaRepository<EstimateContextEntity, String> {
}
