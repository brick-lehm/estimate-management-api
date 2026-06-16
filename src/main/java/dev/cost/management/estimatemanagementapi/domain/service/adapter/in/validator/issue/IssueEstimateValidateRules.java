package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.issue;

import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.rules.EstimateValidateRule;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import project.az.application.adapter.out.output.HttpError;

/**
 * 見積を発行するバリデーションルール
 */
public class IssueEstimateValidateRules
    extends HashMap<Class<?>, List<EstimateValidateRule>> {

  public Optional<HttpError> validates(Object validObject) {

    var key = validObject.getClass();
    for (EstimateValidateRule validator : getOrDefault(key, List.of())) {

      var validateResult = validator.validate(validObject);
      if (validateResult.isPresent()) {
        return validateResult;
      }
    }

    return Optional.empty();
  }
}
