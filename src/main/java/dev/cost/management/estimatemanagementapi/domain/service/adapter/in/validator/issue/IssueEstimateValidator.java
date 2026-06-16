package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.issue;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.rules.ValidationExpirationDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import project.az.application.adapter.out.output.HttpError;

/**
 * 見積を発行する際のバリデーション
 */
@Component("IssueEstimateValidator")
public class IssueEstimateValidator {

  private final IssueEstimateValidateRules rules = (
      new IssueEstimateValidateRules() {{
        /*
        下書きでは、有効期限の有効性のみ検証します。
        */
        put(ExpirationDate.class, List.of(new ValidationExpirationDate()));
      }}
  );

  /**
   * 下書きでは、
   * 全ての入力が必須ではないため入力された項目に対してのみバリデーションを実行する。
   */
  public Optional<HttpError> validate(IssueEstimateContext issueEstimateContext) {

    for (Object validProperty : issueEstimateContext.properties()) {

      var validateResult = rules.validates(validProperty);
      if (validateResult.isPresent()) {
        return validateResult;
      }
    }

    return Optional.empty();
  }
}
