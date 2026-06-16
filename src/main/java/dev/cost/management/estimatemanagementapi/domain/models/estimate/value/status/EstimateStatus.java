package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.status.action.BasedEstimateStatusEditAction;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.methods.status.action.DraftEstimateEditAction;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.EditLineActions;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 見積もりの状態
 */
@Getter
@AllArgsConstructor
public enum EstimateStatus {

  Draft(new DraftEstimateEditAction()), // 下書き
  Submitted(null), // 提出済み
  Approved(null), // 承認済み
  Rejected(null), // 差し戻し
  Withdrawn(null), // 取り下げ済み
  ;

  private final BasedEstimateStatusEditAction basedEstimateStatusEditAction;

  public Lines editLine(Lines lines, EditLineActions editLineActions) {
    var editLine = new AtomicReference<>(lines);

    editLineActions.forEach(editLineAction -> {
      var editedLine = editLineAction.editLines(editLine.get(), basedEstimateStatusEditAction);
      editLine.set(editedLine);
    });

    return editLine.get();
  }
}
