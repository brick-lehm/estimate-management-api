package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository;

import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.DraftEstimateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftEstimateJpaRepository extends JpaRepository<DraftEstimateEntity, String> {
}
