package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount;

import java.math.BigDecimal;

/**
 * 金額
 */
public record Amount(Double amount) {

  /**
   * 金額に対して割り算を行う
   */
  public Amount division(Double dividingValue) {

    var dividedAmount = amount / dividingValue;
    return new Amount(dividedAmount);
  }

  public boolean lessThan(int compared) {
    return amount < compared;
  }

  public BigDecimal bigDecimal() {
    return BigDecimal.valueOf(amount);
  }
}
