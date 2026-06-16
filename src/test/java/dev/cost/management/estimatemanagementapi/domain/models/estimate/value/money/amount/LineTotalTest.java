package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LineTotalTest {

  @Test
  void sum() {
    // 明細単位の合計同士を合算する。
    var total = new LineTotal(11000.0);

    var result = total.sum(new LineTotal(5500.0));

    assertEquals(new LineTotal(16500.0), result);
  }
}
