package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;

/**
 * 明細単位.小計
 */
public record LineSubtotal(Double lineSubtotal) {

  /**
   * 明細単位.合計は、明細単位.小計 + 明細単位.税額の関係である必要があるため
   * LineSubtotalに明細単位.合計メソッドを実装しています。
   */
  public LineTotal lineTotal(LineTax lineTax) {

    return new LineTotal(lineSubtotal + lineTax.lineTax());
  }

  public LineSubtotal round() {
    return new LineSubtotal((double) Math.round(lineSubtotal));
  }

  public LineSubtotal sum(LineSubtotal valuesToSum) {
    var totalValue = lineSubtotal + valuesToSum.lineSubtotal();
    return new LineSubtotal( totalValue );
  }
}
