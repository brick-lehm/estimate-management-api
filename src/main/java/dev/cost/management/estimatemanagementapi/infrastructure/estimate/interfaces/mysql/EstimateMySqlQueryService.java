package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Description;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.LineNo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Quantity;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateStatus;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.out.EstimateQueryService;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.EstimateJoinedRow;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.mapper.EstimateRecordMapper;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateJpaRepository;
import java.util.List;
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
