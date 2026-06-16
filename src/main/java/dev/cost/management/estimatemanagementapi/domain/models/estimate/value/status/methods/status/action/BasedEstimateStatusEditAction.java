package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.status.action;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.AddLineAction;

/**
 * 明細の修正を状態に合わせて行うメソッド
 */
public interface BasedEstimateStatusEditAction {

  Lines editLine(Lines lines, AddLineAction addLineCommand);
}
