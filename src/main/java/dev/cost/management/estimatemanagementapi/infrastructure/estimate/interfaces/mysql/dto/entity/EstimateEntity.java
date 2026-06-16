package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estimate")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateEntity {

  @Id
  @Column(name = "estimate_id", nullable = false, length = 255)
  private String estimateId;
}
