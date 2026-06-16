package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaxClassTest {

  @Test
  void included() {
    // 内税の税区分は included として判定できる。
    assertTrue(TaxClass.included.isIncluded());
    assertFalse(TaxClass.included.isExcluded());
  }

  @Test
  void excluded() {
    // 外税の税区分は excluded として判定できる。
    assertTrue(TaxClass.excluded.isExcluded());
    assertFalse(TaxClass.excluded.isIncluded());
  }
}
