package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import java.util.List;
import project.az.application.adapter.out.output.HttpError;

/**
 * 明細の税区分を外税に固定するポリシー
 */
public class FixExcludedTaxPolicy implements LineTaxClassPolicyMethod {

  @Override
  public void policyCheck(List<Line> lines) {

    for (Line line : lines) {

      fixExcludedTaxPolicy( line.unitPrice() );
    }
  }

  private void fixExcludedTaxPolicy(UnitPrice unitPrice) {

    var taxClass = unitPrice.getTaxClass();
    if (taxClass.isIncluded()) {
      throw new HttpError(400, "明細は、全て外税である必要があります。");
    }
  }
}
