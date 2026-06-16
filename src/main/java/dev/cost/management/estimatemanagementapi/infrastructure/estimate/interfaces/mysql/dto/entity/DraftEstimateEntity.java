package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "draft_estimate")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DraftEstimateEntity {

  @Id
  @Column(name = "estimate_id", nullable = false, length = 255)
  private String estimateId;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public DraftEstimateEntity(String estimateId) {
    this.estimateId = estimateId;
  }
}
