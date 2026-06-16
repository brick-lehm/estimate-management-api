package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import java.util.List;
import project.az.application.adapter.out.output.HttpError;

/**
 * 明細の税区分を『内税』に固定するポリシー
 */
public class FixIncludedTaxPolicy implements LineTaxClassPolicyMethod {

  /**
   * 内税に対する税区分ポリシーチェック
   */
  @Override
  public void policyCheck(List<Line> lines) {

    for (Line line : lines) {
      fixIncludedTaxPolicyCheck( line.unitPrice() );
    }
  }

  private void fixIncludedTaxPolicyCheck(UnitPrice unitPrice) {

    var taxClass = unitPrice.getTaxClass();
    if (taxClass.isExcluded()) {
      throw new HttpError(400, "明細は、全て内税である必要があります。");
    }
  }
}
