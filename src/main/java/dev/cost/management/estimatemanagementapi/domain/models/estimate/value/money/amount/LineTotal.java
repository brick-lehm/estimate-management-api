package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount;

/**
 * 明細単位.合計
 * @param lineTotal
 */
public record LineTotal(Double lineTotal) {

  public LineTotal sum(LineTotal valuesTotal) {
    var total = lineTotal + valuesTotal.lineTotal();
    return new LineTotal(total);
  }
}
