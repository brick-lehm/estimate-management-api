package dev.cost.management.estimatemanagementapi.domain.service;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.events.IssuedEstimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.issue.IssueEstimateValidator;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.out.EstimateQueryService;
import dev.cost.management.estimatemanagementapi.domain.service.factory.IssueDraftEstimate;
import dev.cost.management.estimatemanagementapi.domain.service.interfaces.IssueEstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.az.application.publisher.domain.events.DomainEvents;

/**
 * 下書きの見積を作成するサービス
 */
@Component("IssueDraftEstimateService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueDraftEstimateService implements IssueEstimateService {

  private final IssueDraftEstimate issueDraftEstimate;
  private final IssueEstimateValidator issueEstimateValidator;
  private final EstimateQueryService estimateQueryService;
  private final DomainEvents domainEvents = new DomainEvents();

  @Override
  public Estimate issueEstimate(IssueEstimateContext issueEstimateContext)
  {
    var error = issueEstimateValidator.validate(issueEstimateContext);
    if (error.isPresent()) throw error.get();

    var draftEstimate = issueDraftEstimate.issueEstimate(issueEstimateContext);

    domainEvents.add( new IssuedEstimate(draftEstimate) );
    return draftEstimate;
  }

  /**
   * 同一の見積IDが存在する場合は、TRUE（=存在する）として扱う
   */
  @Override
  public boolean isExists(Estimate issuedEstimate) {
    var estimateEstimateId = issuedEstimate.getEstimateId();
    var fetchedEstimate = estimateQueryService.fetchById(estimateEstimateId);

    return fetchedEstimate.isPresent();
  }

  @Override
  public DomainEvents events() {
    return domainEvents;
  }
}
