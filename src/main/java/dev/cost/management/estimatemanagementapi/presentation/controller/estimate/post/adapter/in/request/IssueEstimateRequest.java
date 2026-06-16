package dev.cost.management.estimatemanagementapi.presentation.controller.estimate.post.adapter.in.request;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import lombok.Getter;

/**
 * 見積を発行するリクエスト
 */
@Getter
public class IssueEstimateRequest {

  private String expirationDate;
  private String customerId;
  private String subject;
  private String memo;
}
