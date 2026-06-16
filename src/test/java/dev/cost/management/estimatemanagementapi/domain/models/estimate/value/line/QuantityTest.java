package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineSubtotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import org.junit.jupiter.api.Test;
import project.az.application.adapter.out.output.HttpError;

class QuantityTest {

  @Test
  void allowOne() {
    // 数量は 1 以上の値を許可する。
    var quantity = new Quantity(1.0);

    assertEquals(1.0, quantity.quantity());
  }

  @Test
  void rejectZero() {
    // 数量が 0 の場合は許可しない。
    var error = assertThrows(HttpError.class, () -> new Quantity(0.0));

    assertEquals(400, error.getStatusCode());
    assertEquals("数量は、0より大きい値である必要があります。", error.getMessage());
  }

  @Test
  void rejectNegative() {
    // 数量が負の値の場合は許可しない。
    var error = assertThrows(HttpError.class, () -> new Quantity(-1.0));

    assertEquals(400, error.getStatusCode());
    assertEquals("数量は、0より大きい値である必要があります。", error.getMessage());
  }

  @Test
  void calcLineTax() {
    // 数量と単価から、丸め前の明細単位の税額を計算する。
    var taxRate = new TaxRate(0.1);
    var taxClass = TaxClass.excluded;
    var excludingTaxAmount = new Amount(10000.0);
    var unitPrice = new UnitPrice(excludingTaxAmount, taxRate, taxClass);

    var quantity = new Quantity(2.0);

    var lineTax = quantity.calcLineTax(unitPrice);
    assertEquals(new LineTax(2000.0), lineTax);
  }

  @Test
  void calcLineSubtotal() {
    // 数量と単価から、丸め前の明細単位の小計を計算する。
    var taxRate = new TaxRate(0.1);
    var taxClass = TaxClass.excluded;
    var excludingTaxAmount = new Amount(10000.0);
    var unitPrice = new UnitPrice(excludingTaxAmount, taxRate, taxClass);

    var quantity = new Quantity(2.0);

    var subtotal = quantity.calcLineSubtotal(unitPrice);
    assertEquals(new LineSubtotal(20000.0), subtotal);
  }
}
