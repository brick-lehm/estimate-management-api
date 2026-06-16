package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository;

import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.EstimateJoinedRow;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimateJpaRepository extends JpaRepository<EstimateEntity, String> {

  @Query(
      value = """
          select
            e.estimate_id as estimateId,
            de.estimate_id as draftEstimateId,
            ec.customer_id as customerId,
            ec.expiration_date as expirationDate,
            ec.subject as subject,
            ec.memo as memo,
            els.line_tax_class_policy as lineTaxClassPolicy,
            el.line_no as lineNo,
            el.description as description,
            el.quantity as quantity,
            el.amount as amount,
            el.tax_rate as taxRate,
            el.tax_class as taxClass
          from estimate e
          left join draft_estimate de on de.estimate_id = e.estimate_id
          left join estimate_context ec on ec.estimate_id = e.estimate_id
          left join estimate_lines els on els.estimate_id = e.estimate_id
          left join estimate_line el on el.estimate_id = e.estimate_id
          where e.estimate_id = :estimateId
          order by el.line_no asc
          """,
      nativeQuery = true
  )
  List<EstimateJoinedRow> fetchByIdWithLeftJoin(@Param("estimateId") String estimateId);
}
