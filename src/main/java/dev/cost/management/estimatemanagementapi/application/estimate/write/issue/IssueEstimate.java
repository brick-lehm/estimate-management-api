package dev.cost.management.estimatemanagementapi.application.estimate.write.issue;

import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.importer.IssueEstimateImporter;
import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.validator.IssueEstimateValidator;
import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.out.exporter.IssueEstimateExporter;
import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.out.result.IssueEstimateResult;
import dev.cost.management.estimatemanagementapi.domain.service.interfaces.IssueEstimateService;
import lombok.AllArgsConstructor;
import project.az.application.adapter.out.output.HttpError;
import project.az.application.publisher.driven.AsyncEventPublisher;

/**
 * 見積を発行するユースケース
 */
@AllArgsConstructor
public class IssueEstimate<T> {

  private final IssueEstimateImporter<T> importer;
  private final IssueEstimateValidator<T> validator;
  private final IssueEstimateExporter exporter;

  private final IssueEstimateService issueEstimateService;

  private final AsyncEventPublisher eventPublisher;

  public IssueEstimateResult handle(T input) {
    // バリデーション
    validator.validate(input);

    var issueEstimateContext = importer.issueEstimateContext(input);
    var issuedEstimate = issueEstimateService.issueEstimate(issueEstimateContext);
    exporter.issuedEstimate(issuedEstimate);

    if (issueEstimateService.isExists(issuedEstimate)) {
      throw new HttpError(409, "同一の見積が存在してます");
    }

    // 出力
    return exporter.export();
  }
}
