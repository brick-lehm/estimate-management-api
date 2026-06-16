package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import java.util.List;

public interface LineTaxClassPolicyMethod {

  void policyCheck(List<Line> lines);
}
