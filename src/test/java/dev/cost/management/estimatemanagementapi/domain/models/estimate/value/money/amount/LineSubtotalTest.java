package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount;

import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;
import org.junit.jupiter.api.Test;

class LineSubtotalTest {

  @Test
  void lineTotal() {
    // 明細単位の合計は、小計に税額を加算して計算する。
    var subtotal = new LineSubtotal(10000.0);

    var total = subtotal.lineTotal(new LineTax(1000.0));

    assertEquals(new LineTotal(11000.0), total);
  }

  @Test
  void round() {
    // 明細単位の小計は四捨五入して整数相当の金額に丸める。
    var subtotal = new LineSubtotal(100.5);

    assertEquals(new LineSubtotal(101.0), subtotal.round());
  }

  @Test
  void sum() {
    // 明細単位の小計同士を合算する。
    var subtotal = new LineSubtotal(10000.0);

    var result = subtotal.sum(new LineSubtotal(2500.0));

    assertEquals(new LineSubtotal(12500.0), result);
  }
}
