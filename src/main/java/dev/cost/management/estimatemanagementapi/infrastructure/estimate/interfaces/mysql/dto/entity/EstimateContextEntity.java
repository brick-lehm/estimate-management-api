package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estimate_context")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateContextEntity {

  @Id
  @Column(name = "estimate_id", nullable = false, length = 255)
  private String estimateId;

  @Column(name = "customer_id", nullable = false, length = 255)
  private String customerId;

  @Column(name = "estimate_status", nullable = false, length = 20)
  private String estimate_status;

  @Column(name = "expiration_date", nullable = false)
  private LocalDateTime expirationDate;

  @Column(name = "subject", nullable = false, length = 255)
  private String subject;

  @Column(name = "memo", nullable = false, columnDefinition = "TEXT")
  private String memo;
}
