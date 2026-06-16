package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.status.action.BasedEstimateStatusEditAction;

/**
 * 明細の修正コマンド/内容
 */
public abstract class EditLineAction {

  /**
   * 見積もりの状態に合わせた明細を修正を行う。
   * 見積もりの状態に合わせた処理自体は、別定義(=ModifyLineStateAction)
   */
  public abstract Lines editLines(Lines lines, BasedEstimateStatusEditAction modifyLineAction);
}
