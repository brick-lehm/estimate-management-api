package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;
import project.az.application.adapter.out.output.HttpError;

/**
 * 承認済みの状態遷移ロジック
 * 他への状態遷移は、ありません。
 */
public class ApprovedFlowMethods implements EstimateStatusFlowMethods {

  @Override
  public EstimateFlowStatus toDraft() {
    throw new HttpError(409,
        "承認済みの見積は下書きに戻せません。内容を変更する場合は、改訂を作成してください。");
  }

  @Override
  public EstimateFlowStatus toSubmitted() {
    throw new HttpError(409,
        "承認済みの見積は提出済みに戻せません。再承認が必要な場合は、改訂を作成して提出してください。");
  }

  @Override
  public EstimateFlowStatus toApproved() {
    throw new HttpError(409,
        "この見積はすでに承認済みです。請求準備で参照するか、内容を変更する場合は改訂を作成してください。");
  }

  @Override
  public EstimateFlowStatus toRejected() {
    throw new HttpError(409,
        "承認済みの見積は差し戻しできません。内容を変更する場合は、改訂を作成してください。");
  }

  @Override
  public EstimateFlowStatus toWithdrawn() {
    throw new HttpError(409,
        "承認済みの見積は取り下げできません。使用しない場合は請求準備候補から選択せず、内容を変更する場合は改訂を作成してください。");
  }
}
