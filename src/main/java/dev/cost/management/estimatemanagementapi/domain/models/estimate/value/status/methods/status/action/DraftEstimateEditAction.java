package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.status.action;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.AddLineAction;

/**
 * 下書き状態の見積もりに対する明細変更処理
 */
public class DraftEstimateEditAction implements BasedEstimateStatusEditAction {

  /**
   * 下書き時は、明細の追加が可能です
   */
  @Override
  public Lines editLine(Lines lines, AddLineAction addLineCommand) {
    return lines.addLine(addLineCommand);
  }
}
