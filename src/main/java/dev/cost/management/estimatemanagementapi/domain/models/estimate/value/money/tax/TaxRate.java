package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax;

import java.math.BigDecimal;
import java.util.Set;
import project.az.application.adapter.out.output.HttpError;

/**
 * 税率
 */
public record TaxRate(Double taxRate) {

  private static final Set<Double> ALLOWED_TAX_RATES = Set.of(0.10, 0.08, 0.00);

  /**
   * - 税率は明細単位で必須とする。
   * - 保存される明細行では必須とする。
   * - 税率はシステムが定義した許可リストから選択する。
   *   利用者が任意の数値を直接入力して保存することは許可しない。
   * - 最小実用版の許可リストは `0.10`、`0.08`、`0.00` を想定する。
   *   実装では税制やテナント設定により許可リストを差し替えられるようにしてよいが、見積保存時点で許可リスト外の値は不可とする。
   * - `0.10` は 10%、`0.08` は 8%、`0.00` は 0% として扱う。
   * - 保存値は小数表現の税率とし、`10` や `8` のようなパーセント値をそのまま保存しない。
   * - 税率は 0 以上 1 未満の数値とする。負の税率、1 以上の税率、数値でない値は不可とする。
   * - 税率は小数第 4 位までを上限とする。最小実用版の許可リストでは小数第 2 位までを使用する。
   * - 税率 0% は非課税、免税、不課税などの税務上の区分を厳密に表すものではなく、最小実用版では税額 0 の明細として扱う。
   */
  public TaxRate {
    if (taxRate == null || taxRate.isNaN() || taxRate.isInfinite()) {
      throw new HttpError(400, "税率は、0以上1未満の数値である必要があります。");
    }

    if (taxRate < 0 || taxRate >= 1) {
      throw new HttpError(400, "税率は、0以上1未満の数値である必要があります。");
    }

    if (BigDecimal.valueOf(taxRate).scale() > 4) {
      throw new HttpError(400, "税率は、小数第4位までである必要があります。");
    }

    if (!ALLOWED_TAX_RATES.contains(taxRate)) {
      throw new HttpError(400, "税率は、許可された値である必要があります。");
    }
  }

  /**
   * 税込換算係数
   */
  public Double conversionFactor() {
    return taxRate + 1;
  }

  public BigDecimal bigDecimal() {
    return BigDecimal.valueOf(taxRate);
  }
}
