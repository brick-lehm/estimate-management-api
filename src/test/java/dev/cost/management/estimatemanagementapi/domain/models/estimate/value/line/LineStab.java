package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;

public class LineStab {

  public static Line create(Amount excludingTaxAmount, TaxClass taxClass) {
    var taxRate = new TaxRate(0.1);
    var unitPrice = new UnitPrice(excludingTaxAmount, taxRate, taxClass);

    var quantity = new Quantity(2.0);
    return new Line(new LineNo(1), new Description("test"), quantity, unitPrice);
  }

  public static Line create(Amount excludingTaxAmount) {
    return LineStab.create(excludingTaxAmount, TaxClass.excluded);
  }

  public static Line create() {
    return LineStab.create( new Amount(10000.0) );
  }
}
