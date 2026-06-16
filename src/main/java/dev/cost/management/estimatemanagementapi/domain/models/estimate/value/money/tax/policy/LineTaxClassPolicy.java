package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * 税区分を固定するか、しないか
 */
@AllArgsConstructor
public enum LineTaxClassPolicy {
  included(new FixIncludedTaxPolicy()), // 内税
  excluded(new FixExcludedTaxPolicy()), // 外税
  // mix(null), // 混合
  ;

  private final LineTaxClassPolicyMethod lineTaxClassPolicyMethod;

  /**
   * 明細に対する税区分ポリシーチェック
   */
  public void policyCheck(List<Line> lines) {
    lineTaxClassPolicyMethod.policyCheck(lines);
  }
}
