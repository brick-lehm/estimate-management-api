package dev.cost.management.estimatemanagementapi.domain.models.estimate.root;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;
import lombok.AllArgsConstructor;

/**
 * 集約: 見積もり
 * 責務: 見積もりフローの管理
 */
@AllArgsConstructor
public class EstimateFlow {

  private final EstimateId estimateId;
  private ExpirationDate expirationDate;
  private EstimateFlowStatus estimateFlowStatus;

}
