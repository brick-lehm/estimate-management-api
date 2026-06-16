package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineSubtotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineTotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;

/**
 * 明細
 */
public record Line(
    LineNo lineNo, Description description,
    Quantity quantity, UnitPrice unitPrice ) {

  /**
   * 明細単位.小計 = round(税抜金額)
   */
  public LineSubtotal lineSubtotal() {
    var lineSubtotal = quantity.calcLineSubtotal(unitPrice);
    return lineSubtotal.round();
  }

  /**
   * 明細単位.税額 = round(税額 * 数量)
   */
  public LineTax lineTax() {
    var lineTax = quantity.calcLineTax(unitPrice);
    return lineTax.round();
  }

  /**
   * 明細単位.合計 = 明細単位.小計 + 明細単位.税額
   */
  public LineTotal lineTotal() {
    var lineSubtotal = lineSubtotal();
    var lineTax = lineTax();

    return lineSubtotal.lineTotal(lineTax);
  }
}
