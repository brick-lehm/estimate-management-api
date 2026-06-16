package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estimate_line")
@IdClass(EstimateLineId.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateLineEntity {

  @Id
  @Column(name = "estimate_id", nullable = false, length = 255)
  private String estimateId;

  @Id
  @Column(name = "line_no", nullable = false)
  private int lineNo;

  @Column(name = "description", nullable = false, length = 255)
  private String description;

  @Column(name = "quantity", nullable = false, precision = 10, scale = 4)
  private BigDecimal quantity;

  @Column(name = "amount", nullable = false, precision = 10, scale = 4)
  private BigDecimal amount;

  @Column(name = "tax_rate", nullable = false, precision = 10, scale = 4)
  private BigDecimal taxRate;

  @Column(name = "tax_class", nullable = false, length = 20)
  private String taxClass;
}
