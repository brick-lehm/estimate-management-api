package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Description;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Quantity;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.status.action.BasedEstimateStatusEditAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 明細を追加するコマンド
 */
@Getter
@AllArgsConstructor
public class AddLineAction extends EditLineAction {

  private final Description description;
  private final Quantity  quantity;
  private final UnitPrice unitPrice;

  /**
   * 見積もりの状態に合わせた明細修正を行います。
   * 実際に明細修正は、状態に合わせた処理が実装されたクラスで行われます。
   */
  @Override
  public Lines editLines(Lines lines, BasedEstimateStatusEditAction action) {
    return action.editLine(lines, this);
  }
}
