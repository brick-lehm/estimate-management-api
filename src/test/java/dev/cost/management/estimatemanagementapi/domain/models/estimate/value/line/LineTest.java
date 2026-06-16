package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineSubtotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineTotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;
import org.junit.jupiter.api.Test;

class LineTest {

  @Test
  void lineSubtotal() {
    // 数量と税抜単価から明細単位の小計を計算する。
    var line = LineStab.create();

    assertEquals(new LineSubtotal(20000.0), line.lineSubtotal());
  }

  @Test
  void lineTax() {
    // 数量、単価、税率から明細単位の税額を計算する。
    var line = LineStab.create();

    assertEquals(new LineTax(2000.0), line.lineTax());
  }

  @Test
  void lineTotal() {
    // 明細単位の合計は、小計と税額を加算して計算する。
    var line = LineStab.create();

    assertEquals(new LineTotal(22000.0), line.lineTotal());
  }
}
