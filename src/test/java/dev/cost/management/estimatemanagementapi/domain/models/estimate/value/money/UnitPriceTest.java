package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money;

import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxAmount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import org.junit.jupiter.api.Test;
import project.az.application.adapter.out.output.HttpError;

class UnitPriceTest {

  @Test
  void allowZero() {
    // 単価は 0 以上の値を許可する。
    var unitPrice = new UnitPrice(new Amount(0.0), new TaxRate(0.1), TaxClass.excluded);

    assertEquals(new Amount(0.0), unitPrice.amount());
  }

  @Test
  void rejectNegativeAmount() {
    // 単価が負の値の場合は許可しない。
    var error = assertThrows(
        HttpError.class,
        () -> new UnitPrice(new Amount(-1.0), new TaxRate(0.1), TaxClass.excluded)
    );

    assertEquals(400, error.getStatusCode());
    assertEquals("単価は、0以上である必要があります。", error.getMessage());
  }

  @Test
  void amountWhenExcludedTax() {
    // 外税の場合、入力金額は税抜金額としてそのまま扱う。
    var unitPrice = new UnitPrice(new Amount(10000.0), new TaxRate(0.1), TaxClass.excluded);

    assertEquals(new Amount(10000.0), unitPrice.amount());
  }

  @Test
  void amountWhenIncludedTax() {
    // 内税の場合、入力金額を税込金額として扱い、税抜金額に換算する。
    var unitPrice = new UnitPrice(new Amount(11000.0), new TaxRate(0.1), TaxClass.included);

    assertEquals(new Amount(10000.0), unitPrice.amount());
  }

  @Test
  void taxAmountWhenExcludedTax() {
    // 外税の場合、税抜金額に税率を掛けて税額を計算する。
    var unitPrice = new UnitPrice(new Amount(10000.0), new TaxRate(0.1), TaxClass.excluded);

    assertEquals(new TaxAmount(1000.0), unitPrice.taxAmount());
  }

  @Test
  void taxAmountWhenIncludedTax() {
    // 内税の場合、税抜換算後の金額に税率を掛けて税額を計算する。
    var unitPrice = new UnitPrice(new Amount(11000.0), new TaxRate(0.1), TaxClass.included);

    assertEquals(new TaxAmount(1000.0), unitPrice.taxAmount());
  }
}
