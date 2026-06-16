package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.mapper;

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
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.EstimateJoinedRow;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EstimateReadRecordMapper {

  public boolean isMissingRequiredRows(EstimateJoinedRow row) {
    
    return row.getDraftEstimateId() == null
        || row.getCustomerId() == null
        || row.getExpirationDate() == null
        || row.getSubject() == null
        || row.getMemo() == null
        || row.getLineTaxClassPolicy() == null;
  }

  public Estimate existingEstimate(EstimateId estimateId, List<EstimateJoinedRow> rows) {
    var firstRow = rows.getFirst();

    return new Estimate(
        estimateId,
        new CustomerId(firstRow.getCustomerId()),
        new ExpirationDate(firstRow.getExpirationDate()),
        EstimateStatus.Draft,
        new Subject(firstRow.getSubject()),
        new Memo(firstRow.getMemo()),
        new Lines(
            rows.stream()
                .filter(row -> row.getLineNo() != null)
                .map(this::line)
                .toList(),
            LineTaxClassPolicy.valueOf(firstRow.getLineTaxClassPolicy())
        )
    );
  }

  public Line line(EstimateJoinedRow row) {
    return new Line(
        new LineNo(row.getLineNo()),
        new Description(row.getDescription()),
        new Quantity(row.getQuantity().doubleValue()),
        new UnitPrice(
            new Amount(row.getAmount().doubleValue()),
            new TaxRate(row.getTaxRate().doubleValue()),
            TaxClass.valueOf(row.getTaxClass())
        )
    );
  }
}
