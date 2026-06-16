package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows.ApprovedFlowMethods;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows.DraftFlowMethods;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows.EstimateStatusFlowMethods;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows.RejectedFlowMethods;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows.SubmittedFlowMethods;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows.WithdrawnFlowMethods;
import lombok.AllArgsConstructor;

/**
 * 見積フローの状態
 */
@AllArgsConstructor
public enum EstimateFlowStatus {

  Draft(new DraftFlowMethods()), // 下書き
  Submitted(new SubmittedFlowMethods()), // 提出済み
  Approved(new ApprovedFlowMethods()), // 承認済み
  Rejected(new RejectedFlowMethods()), // 差し戻し
  Withdrawn(new WithdrawnFlowMethods()), // 取り下げ済み
  ;

  private final EstimateStatusFlowMethods statusFlowMethods;
}
