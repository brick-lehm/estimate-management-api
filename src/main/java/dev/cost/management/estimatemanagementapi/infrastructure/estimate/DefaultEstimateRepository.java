package dev.cost.management.estimatemanagementapi.infrastructure.estimate;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.out.EstimateRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.EstimateOutportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 本番環境向け：永続化されることが前提とされたリポジトリ具象クラス
 */
@Component
@AllArgsConstructor( onConstructor_ = @Autowired )
public class DefaultEstimateRepository implements EstimateRepository {

  private final EstimateOutportStrategy estimateOutportStrategy;

  @Override
  public void save(Estimate estimate) {
    estimateOutportStrategy.save(estimate);
  }
}
