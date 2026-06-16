package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface EstimateJoinedRow {

  String getEstimateId();

  String getDraftEstimateId();

  String getCustomerId();

  LocalDateTime getExpirationDate();

  String getSubject();

  String getMemo();

  String getLineTaxClassPolicy();

  Integer getLineNo();

  String getDescription();

  BigDecimal getQuantity();

  BigDecimal getAmount();

  BigDecimal getTaxRate();

  String getTaxClass();
}
