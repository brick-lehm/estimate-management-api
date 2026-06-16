package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.mapper;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.EstimateJoinedRow;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.EstimateRecord;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"mysql", "default"})
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EstimateRecordMapper {

  private final EstimateWriteRecordMapper writeRecordMapper;
  private final EstimateReadRecordMapper readRecordMapper;

  public Optional<Estimate> toReadConvert(EstimateId estimateId, List<EstimateJoinedRow> rows) {
    if (rows.isEmpty()) return Optional.empty();
    if (readRecordMapper.isMissingRequiredRows(rows.getFirst())) return Optional.empty();

    var readedEstimate = readRecordMapper.existingEstimate(estimateId, rows);
    return Optional.of( readedEstimate );
  }

  public EstimateRecord toWriteConvert(Estimate estimate) {
    var estimateId = estimate.getEstimateId();

    return new EstimateRecord(
        writeRecordMapper.estimateEntity( estimate.getEstimateId() ),
        writeRecordMapper.estimateContextEntity( estimate ),
        writeRecordMapper.estimateLinesEntity( estimateId, estimate.getLines() ),
        writeRecordMapper.estimateLineEntities( estimateId, estimate.getLines() )
    );
  }
}
