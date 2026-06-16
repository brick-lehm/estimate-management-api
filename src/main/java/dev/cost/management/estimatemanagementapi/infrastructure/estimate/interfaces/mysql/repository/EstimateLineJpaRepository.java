package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository;

import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLineEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLineId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateLineJpaRepository extends JpaRepository<EstimateLineEntity, EstimateLineId> {

  List<EstimateLineEntity> findByEstimateIdOrderByLineNoAsc(String estimateId);
}
