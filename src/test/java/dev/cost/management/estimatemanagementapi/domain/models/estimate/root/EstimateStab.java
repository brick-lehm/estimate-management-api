package dev.cost.management.estimatemanagementapi.domain.models.estimate.root;

import static dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy.excluded;
import static java.time.LocalDateTime.of;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Description;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.LineStab;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Quantity;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateStatus;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.AddLineAction;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class EstimateStab {

  public static final String ESTIMATE_ID = "est_%s".formatted( UUID.randomUUID() );
  public static final String CUSTOMER_ID = "customer-001";
  public static final String SUBJECT = "見積件名";
  public static final String MEMO = "メモ";

  public static Estimate create() {
    var addLineAction = new AddLineAction(
        new Description("test"),
        new Quantity(2.0),
        new UnitPrice(
            new Amount(200.0),
            new TaxRate(0.1),
            TaxClass.excluded
        )
    );
    var lines = new Lines(excluded);
    lines = lines.addLine(addLineAction);
    lines = lines.addLine(addLineAction);

    return create(lines);
  }

  public static Estimate create(Lines lines) {
    return create(EstimateStatus.Draft, lines);
  }

  public static Estimate create(EstimateStatus estimateStatus, Lines lines) {
    return new Estimate(
        new EstimateId(ESTIMATE_ID),
        new CustomerId(CUSTOMER_ID),
        new ExpirationDate(of(2026, 5, 12, 15, 30)),
        estimateStatus,
        new Subject(SUBJECT),
        new Memo(MEMO),
        lines
    );
  }

  public static Estimate create(List<Line> lines, LineTaxClassPolicy lineTaxClassPolicy) {
    return create(new Lines(lines, lineTaxClassPolicy));
  }
}
