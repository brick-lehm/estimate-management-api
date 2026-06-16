package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax;

/**
 * 税区分
 */
public enum TaxClass {

  included, // 内税
  excluded, // 外税
  ;

  public boolean isExcluded() {
    return equals(excluded);
  }

  public boolean isIncluded() {
    return equals(included);
  }
}
