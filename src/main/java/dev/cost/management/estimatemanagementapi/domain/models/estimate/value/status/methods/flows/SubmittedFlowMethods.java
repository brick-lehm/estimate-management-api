package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;
import project.az.application.adapter.out.output.HttpError;

/**
 * 提出済みの状態遷移ロジック
 */
public class SubmittedFlowMethods implements EstimateStatusFlowMethods {

  @Override
  public EstimateFlowStatus toDraft() {
    throw new HttpError(
        409,
        "提出済みの見積は下書きに戻せません。内容を修正する場合は、取り下げまたは差し戻し後に改訂を作成してください。"
    );
  }

  @Override
  public EstimateFlowStatus toSubmitted() {
    throw new HttpError(
        409,
        "この見積はすでに提出済みです。承認、差し戻し、または取り下げを行ってください。"
    );
  }

  @Override
  public EstimateFlowStatus toApproved() {
    return EstimateFlowStatus.Approved;
  }

  @Override
  public EstimateFlowStatus toRejected() {
    return EstimateFlowStatus.Rejected;
  }

  @Override
  public EstimateFlowStatus toWithdrawn() {
    return EstimateFlowStatus.Withdrawn;
  }
}
