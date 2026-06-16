package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxAmount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * お金
 * Amount が Tax の影響を受ける関係です。
 * Tax(=内税)である場合には、Amountは、税込として扱われます。
 * Tax(=外税)である場合には、Amountは、税抜として扱われます。
 * ---
 * システム内部の計算には必ず外税である必要である仕様を考慮し、
 * 内税である場合には`外税`に置き換えてから返却します。
 */
@Getter
@AllArgsConstructor
public class Money {

  private final Amount amount;
  private final TaxRate taxRate;
  private final TaxClass taxClass;

  /**
   * 税額 = 金額 * 税率
   */
  public TaxAmount taxAmount() {
    var multiplierTaxRate = taxRate.taxRate();
    var amountExcludingTax = amount().amount();

    return new TaxAmount(amountExcludingTax * multiplierTaxRate);
  }

  /**
   * 税抜金額
   */
  public Amount amount() {
    if (taxClass.isExcluded()) {
      return amount;
    }

    // 入力時の税込明細金額 / (1 + 税率)
    var conversionFactorIncludingTax = taxRate.conversionFactor();
    return amount.division(conversionFactorIncludingTax);
  }
}
