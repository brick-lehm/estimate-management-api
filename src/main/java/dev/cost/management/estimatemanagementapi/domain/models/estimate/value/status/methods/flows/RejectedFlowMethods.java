package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.flows;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateFlowStatus;
import project.az.application.adapter.out.output.HttpError;

/**
 * 差し戻しの状態遷移ロジック
 * 他への状態遷移はありません。
 * 見積もりの改訂を利用し他ステータスへ遷移する必要があります。
 */
public class RejectedFlowMethods implements EstimateStatusFlowMethods {

  @Override
  public EstimateFlowStatus toDraft() {
    throw new HttpError(409,
        "差し戻し済みの見積は下書きに戻せません。内容を修正する場合は、改訂を作成してください。");
  }

  @Override
  public EstimateFlowStatus toSubmitted() {
    throw new HttpError(409,
        "差し戻し済みの見積はそのまま再提出できません。差し戻し内容を反映した改訂を作成して提出してください。"
    );
  }

  @Override
  public EstimateFlowStatus toApproved() {
    throw new HttpError(409,
        "差し戻し済みの見積は直接承認できません。改訂を作成し、提出してから承認してください。"
    );
  }

  @Override
  public EstimateFlowStatus toRejected() {
    throw new HttpError(409,
        "この見積はすでに差し戻し済みです。内容を修正する場合は、改訂を作成してください。"
    );
  }

  @Override
  public EstimateFlowStatus toWithdrawn() {
    throw new HttpError(409,
        "差し戻し済みの見積は取り下げできません。" +
        "使用しない場合はそのまま履歴として保持し、再利用する場合は改訂または複製を作成してください。"
    );
  }
}
