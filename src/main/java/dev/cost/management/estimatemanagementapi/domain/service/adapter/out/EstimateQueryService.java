package dev.cost.management.estimatemanagementapi.domain.service.adapter.out;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import java.util.Optional;

public interface EstimateQueryService {

  /**
   * 見積IDをもとに見積を参照する
   */
  Optional<Estimate> fetchById(EstimateId estimateEstimateId);
}
