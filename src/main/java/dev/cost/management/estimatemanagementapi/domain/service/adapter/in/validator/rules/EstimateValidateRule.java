package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.rules;

import java.util.Optional;
import project.az.application.adapter.out.output.HttpError;

public abstract class EstimateValidateRule {

  public abstract Optional<HttpError> validate(Object validObject);
}
