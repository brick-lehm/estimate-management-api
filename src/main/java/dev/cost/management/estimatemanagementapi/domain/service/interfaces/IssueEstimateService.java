package dev.cost.management.estimatemanagementapi.domain.service.interfaces;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;
import project.az.application.publisher.domain.events.DomainService;

/**
 * 見積を発行するドメインサービス
 */
public interface IssueEstimateService extends DomainService {

  /**
   * 見積を発行する
   */
  Estimate issueEstimate(IssueEstimateContext issueEstimateContext);

  /**
   * 見積が存在するかを判定する
   */
  boolean isExists(Estimate issuedEstimate);
}
