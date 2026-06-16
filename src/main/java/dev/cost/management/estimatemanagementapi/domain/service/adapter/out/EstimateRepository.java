package dev.cost.management.estimatemanagementapi.domain.service.adapter.out;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;

public interface EstimateRepository {

  void save(Estimate estimate);
}
