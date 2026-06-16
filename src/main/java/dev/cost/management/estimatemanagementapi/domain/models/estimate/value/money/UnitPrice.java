package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import project.az.application.adapter.out.output.HttpError;

/**
 * 単価
 */
public class UnitPrice extends Money {

  public UnitPrice(
      Amount amount,
      TaxRate taxRate, TaxClass taxClass
  ) {
    super(amount, taxRate, taxClass);

    if (amount.lessThan(0)) {
      throw new HttpError(400, "単価は、0以上である必要があります。");
    }
  }
}
