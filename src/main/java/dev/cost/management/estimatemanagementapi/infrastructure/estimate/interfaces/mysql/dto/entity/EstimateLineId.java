package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EstimateLineId implements Serializable {

  private String estimateId;
  private int lineNo;
}
