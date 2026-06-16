package dev.cost.management.estimatemanagementapi.domain.models.estimate.root;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateStatus;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.EditLineActions;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 集約:アクティブレコード
 * 責務:見積もり情報の一貫性管理
 */
@Getter
@AllArgsConstructor
public class Estimate {

  private final EstimateId estimateId;
  private CustomerId customerId;

  private ExpirationDate expirationDate;
  private EstimateStatus estimateStatus;

  private Subject subject;
  private Memo memo;

  private Lines lines;
}
