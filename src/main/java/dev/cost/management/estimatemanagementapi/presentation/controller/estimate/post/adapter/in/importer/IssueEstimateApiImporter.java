package dev.cost.management.estimatemanagementapi.presentation.controller.estimate.post.adapter.in.importer;

import static project.az.application.shared.Strings.isNullOrEmpty;

import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.importer.IssueEstimateImporter;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;
import dev.cost.management.estimatemanagementapi.presentation.controller.estimate.post.adapter.in.request.IssueEstimateRequest;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 * 見積発行ユースケースに関するリクエストを変換する操作を提供します
 */
@Component("IssueEstimateApiImporter")
public class IssueEstimateApiImporter implements IssueEstimateImporter<IssueEstimateRequest> {

  @Override
  public IssueEstimateContext issueEstimateContext(IssueEstimateRequest input) {

    return IssueEstimateContext.builder()
        .expirationDate( expirationDate(input.getExpirationDate()) )
        .customerId( new CustomerId( input.getCustomerId() ) )
        .subject( new Subject(input.getSubject()) )
        .memo( new Memo( input.getMemo() ) )
        .build();
  }

  private ExpirationDate expirationDate(String expirationDate) {
    if (isNullOrEmpty( expirationDate )) {
      return ExpirationDate.notYetDecided();
    }

    var expirationDatetime = LocalDateTime.parse(expirationDate);
    return new ExpirationDate(expirationDatetime);
  }
}
