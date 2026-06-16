package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CurrencyTest {

  @Test
  void jpy() {
    // 通貨は JPY を扱う。
    assertEquals(Currency.JPY, Currency.valueOf("JPY"));
  }
}
