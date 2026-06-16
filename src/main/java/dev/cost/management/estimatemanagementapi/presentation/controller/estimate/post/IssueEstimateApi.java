package dev.cost.management.estimatemanagementapi.presentation.controller.estimate.post;

import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.IssueEstimate;
import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.importer.IssueEstimateImporter;
import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.validator.IssueEstimateValidator;
import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.out.exporter.IssueEstimateExporter;
import dev.cost.management.estimatemanagementapi.domain.service.interfaces.IssueEstimateService;
import dev.cost.management.estimatemanagementapi.presentation.controller.estimate.post.adapter.in.request.IssueEstimateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import project.az.application.publisher.driven.AsyncEventPublisher;

/**
 * 見積を発行するAPI
 */
@Component
public class IssueEstimateApi extends IssueEstimate<IssueEstimateRequest> {

  public IssueEstimateApi(
      @Qualifier("IssueEstimateApiImporter")
      IssueEstimateImporter<IssueEstimateRequest> importer,
      IssueEstimateValidator<IssueEstimateRequest> validator,
      IssueEstimateExporter exporter,
      @Qualifier("IssueDraftEstimateService")
      IssueEstimateService issueEstimateService,
      AsyncEventPublisher eventPublisher
  ) {
    super(importer, validator, exporter, issueEstimateService, eventPublisher);
  }
}
