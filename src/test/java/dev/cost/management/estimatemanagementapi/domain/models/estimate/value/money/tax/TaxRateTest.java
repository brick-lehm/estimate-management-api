package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.az.application.adapter.out.output.HttpError;

class TaxRateTest {

  @ParameterizedTest
  @ValueSource(doubles = {0.10, 0.08, 0.00})
  void allowPermittedTaxRates(Double value) {
    // 最小実用版の許可リストに含まれる税率を許可する。
    var taxRate = new TaxRate(value);

    assertEquals(value, taxRate.taxRate());
  }

  @Test
  void rejectNull() {
    // 税率は必須である。
    var error = assertThrows(HttpError.class, () -> new TaxRate(null));

    assertEquals(400, error.getStatusCode());
    assertEquals("税率は、0以上1未満の数値である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {-0.01, 1.00})
  void rejectOutOfRange(Double value) {
    // 税率は 0 以上 1 未満の数値のみ許可する。
    var error = assertThrows(HttpError.class, () -> new TaxRate(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("税率は、0以上1未満の数値である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY})
  void rejectNotNumber(Double value) {
    // 数値でない値や無限大は税率として許可しない。
    var error = assertThrows(HttpError.class, () -> new TaxRate(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("税率は、0以上1未満の数値である必要があります。", error.getMessage());
  }

  @Test
  void rejectMoreThanFourthDecimalPlace() {
    // 税率は小数第 4 位までとし、それを超える精度の値は許可しない。
    var error = assertThrows(HttpError.class, () -> new TaxRate(0.10001));

    assertEquals(400, error.getStatusCode());
    assertEquals("税率は、小数第4位までである必要があります。", error.getMessage());
  }

  @Test
  void rejectNotPermittedTaxRate() {
    // 許可リスト外の税率は保存時点の値として許可しない。
    var error = assertThrows(HttpError.class, () -> new TaxRate(0.05));

    assertEquals(400, error.getStatusCode());
    assertEquals("税率は、許可された値である必要があります。", error.getMessage());
  }

  @Test
  void conversionFactor() {
    // 税込金額から税抜金額へ換算するため、税率に 1 を加えた係数を返す。
    var taxRate = new TaxRate(0.1);

    assertEquals(1.1, taxRate.conversionFactor());
  }
}
