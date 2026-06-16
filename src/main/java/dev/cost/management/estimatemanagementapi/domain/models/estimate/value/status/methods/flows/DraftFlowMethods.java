package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;
import project.az.application.adapter.out.output.HttpError;

/**
 * 下書きステータス時の各ステータス遷移実装
 */
public class DraftFlowMethods implements EstimateStatusFlowMethods {

  @Override
  public EstimateFlowStatus toDraft() {
    throw new HttpError(409, "この見積はすでに下書きです。提出または取り下げを行ってください。");
  }

  @Override
  public EstimateFlowStatus toSubmitted() {
    return EstimateFlowStatus.Submitted;
  }

  @Override
  public EstimateFlowStatus toApproved() {
    throw new HttpError(409, "下書きの見積は直接承認できません。提出してから承認してください。");
  }

  @Override
  public EstimateFlowStatus toRejected() {
    throw new HttpError(409, "下書きの見積は差し戻しできません。差し戻しは提出済みの見積に対して行ってください。");
  }

  @Override
  public EstimateFlowStatus toWithdrawn() {
    return EstimateFlowStatus.Withdrawn;
  }
}
