package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.mapper;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Line;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateContextEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLineEntity;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLinesEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EstimateWriteRecordMapper {

  public EstimateLinesEntity estimateLinesEntity(EstimateId estimateId, Lines lines) {
    var lineTaxClassPolicy = lines.lineTaxClassPolicy();

    return new EstimateLinesEntity(
        estimateId.estimateId(),
        lineTaxClassPolicy.name()
    );
  }

  public List<EstimateLineEntity> estimateLineEntities(EstimateId estimateId, Lines lines) {

    return lines.lines().stream().reduce(
        new ArrayList<>(),
        (accumulator, line) -> {
          accumulator.add(estimateLineEntity(estimateId, line));
          return accumulator;
        },
        (left, right) -> {
          left.addAll(right);
          return left;
        }
    );
  }

  public EstimateLineEntity estimateLineEntity(EstimateId estimateId, Line line) {
    var lineNo = line.lineNo();
    var description = line.description();
    var quantity = line.quantity();

    var unitPrice = line.unitPrice();
    var amount = unitPrice.amount();
    var taxRate = unitPrice.getTaxRate();
    var taxClass = unitPrice.getTaxClass();

    return new EstimateLineEntity(
        estimateId.estimateId(),
        lineNo.lineNo(),
        description.description(),
        BigDecimal.valueOf(quantity.quantity()),
        amount.bigDecimal(),
        taxRate.bigDecimal(),
        taxClass.name()
    );
  }

  public EstimateContextEntity estimateContextEntity(Estimate estimate) {
    var estimateId = estimate.getEstimateId();
    var customerId = estimate.getCustomerId();
    var estimateStatus = estimate.getEstimateStatus();
    var expirationDate = estimate.getExpirationDate();
    var subject = estimate.getSubject();
    var memo = estimate.getMemo();

    return new EstimateContextEntity(
        estimateId.estimateId(),
        customerId.customerId(),
        estimateStatus.name(),
        expirationDate.getCurrent(),
        subject.subject(),
        memo.memo()
    );
  }

  public EstimateEntity estimateEntity(EstimateId estimateId) {
    return new EstimateEntity(estimateId.estimateId());
  }
}
