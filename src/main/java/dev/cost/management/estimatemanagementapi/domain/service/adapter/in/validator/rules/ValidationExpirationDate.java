package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.rules;

import static java.time.LocalDate.now;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import java.util.Optional;
import project.az.application.adapter.out.output.HttpError;
import project.az.domain.model.value.datetime.Datetime;

public class ValidationExpirationDate extends EstimateValidateRule {

  /**
   * ## 有効期限
   * - 過去日は不可とする。
   * - 有効期限当日は有効とみなす。
   */
  @Override
  public Optional<HttpError> validate(Object validObject) {
    if ( !(validObject instanceof ExpirationDate expirationDate) ) return Optional.empty();

    var today = new Datetime(now().atStartOfDay());
    if (expirationDate.lessThan(today)) {
      return Optional.of(new HttpError(400, "有効期限は、当日以降の日付である必要があります。"));
    }
    return Optional.empty();
  }
}
