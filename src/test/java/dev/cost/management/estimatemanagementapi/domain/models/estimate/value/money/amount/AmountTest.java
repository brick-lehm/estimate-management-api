package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AmountTest {

  @Test
  void division() {
    // 金額を指定した値で割り、割り算後の金額を返す。
    var amount = new Amount(11000.0);

    var result = amount.division(1.1);

    assertEquals(new Amount(10000.0), result);
  }

  @Test
  void lessThan() {
    // 金額が比較対象より小さい場合は true を返す。
    var amount = new Amount(-1.0);

    assertTrue(amount.lessThan(0));
  }
}
