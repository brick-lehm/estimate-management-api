package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineSubtotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;
import project.az.application.adapter.out.output.HttpError;

/**
 * 数量
 * @param quantity
 */
public record Quantity(Double quantity) {

  public Quantity {
    if (quantity < 1) {
      throw new HttpError(400, "数量は、0より大きい値である必要があります。");
    }
  }

  /**
   * 明細単位.小計(=税抜) = 数量 * 単価
   * 数量 * 税抜単価で明細単位.小計を求める必要があるため
   * Quantityに明細単位.小計を計算するためのメソッドを実装しています。
   */
  public LineSubtotal calcLineSubtotal(UnitPrice unitPrice) {
    var excludingTaxAmount = unitPrice.amount();
    var amountExcludingTax = quantity * excludingTaxAmount.amount();

    return new LineSubtotal(amountExcludingTax);
  }

  /**
   * 明細単位.税額:
   * 数量 * 税込単価 の式で明細単位.税額を求める必要があるため
   * Quantityに明細単位.税額を計算するためのメソッドを実装しています。
   */
  public LineTax calcLineTax(UnitPrice unitPrice) {
    var unitTaxAmount = unitPrice.taxAmount();
    var taxAmount = quantity * unitTaxAmount.taxAmount();

    return new LineTax(taxAmount);
  }
}
