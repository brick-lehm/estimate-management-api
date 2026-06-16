package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax;

/**
 * 明細単位.税額
 * @param lineTax
 */
public record LineTax(Double lineTax) {

  public LineTax round() {
    return new LineTax( (double) Math.round(lineTax) );
  }

  public LineTax sum(LineTax valuesTotal) {
    var total = lineTax + valuesTotal.lineTax();
    return new LineTax(total);
  }
}
