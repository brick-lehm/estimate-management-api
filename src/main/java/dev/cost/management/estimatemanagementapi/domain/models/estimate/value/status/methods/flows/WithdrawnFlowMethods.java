package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;
import project.az.application.adapter.out.output.HttpError;

/**
 * 取り下げ済みの状態遷移ロジック
 */
public class WithdrawnFlowMethods implements EstimateStatusFlowMethods {

  @Override
  public EstimateFlowStatus toDraft() {
    throw new HttpError(409,
        "取り下げ済みの見積は下書きに戻せません。再利用する場合は、複製または改訂を作成してください。");
  }

  @Override
  public EstimateFlowStatus toSubmitted() {
    throw new HttpError(409,
        "取り下げ済みの見積はそのまま提出できません。再利用する場合は、複製または改訂を作成して提出してください。");
  }

  @Override
  public EstimateFlowStatus toApproved() {
    throw new HttpError(409,
        "取り下げ済みの見積は直接承認できません。再利用する場合は、複製または改訂を作成し、提出してから承認してください。");
  }

  @Override
  public EstimateFlowStatus toRejected() {
    throw new HttpError(409,
        "取り下げ済みの見積は差し戻しできません。差し戻しは提出済みの見積に対して行ってください。");
  }

  @Override
  public EstimateFlowStatus toWithdrawn() {
    throw new HttpError(409,
        "この見積はすでに取り下げ済みです。再利用する場合は、複製または改訂を作成してください。");
  }
}
