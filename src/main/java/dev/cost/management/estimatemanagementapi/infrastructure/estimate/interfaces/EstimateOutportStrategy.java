package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;

public interface EstimateOutportStrategy {

  void save(Estimate estimate);
}
