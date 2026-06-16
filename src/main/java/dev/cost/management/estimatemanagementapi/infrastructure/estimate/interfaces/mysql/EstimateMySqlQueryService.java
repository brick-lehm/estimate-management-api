package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.out.EstimateQueryService;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.mapper.EstimateRecordMapper;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateJpaRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"mysql", "default"})
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EstimateMySqlQueryService implements EstimateQueryService {

  private final EstimateJpaRepository estimateJpaRepository;
  private final EstimateRecordMapper estimateRecordMapper;

  @Override
  public Optional<Estimate> fetchById(EstimateId estimateEstimateId) {
    var estimateId = estimateEstimateId.estimateId();
    var rows = estimateJpaRepository.fetchByIdWithLeftJoin(estimateId);

    return estimateRecordMapper.toReadConvert(estimateEstimateId, rows);
  }
}
