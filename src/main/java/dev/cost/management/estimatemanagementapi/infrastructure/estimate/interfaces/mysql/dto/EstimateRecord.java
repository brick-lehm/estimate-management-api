package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto;

import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.DraftEstimateEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateContextEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLineEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLinesEntity;
import java.util.List;

public record EstimateRecord(
    EstimateEntity estimate,
    EstimateContextEntity estimateContext,
    EstimateLinesEntity lines,
    List<EstimateLineEntity> line
) {

  public DraftEstimateEntity stateEstimate() {
    return new DraftEstimateEntity(
        estimate.getEstimateId()
    );
  }
}
