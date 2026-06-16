package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;

public interface EstimateStatusFlowMethods {

  EstimateFlowStatus toDraft();
  EstimateFlowStatus toSubmitted();
  EstimateFlowStatus toApproved();
  EstimateFlowStatus toRejected();
  EstimateFlowStatus toWithdrawn();
}
