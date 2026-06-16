package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"mysql"})
@AllArgsConstructor(onConstructor_ = @Autowired)
class EstimateMySqlQueryServiceTest {

  private final EstimateJpaRepository estimateJpaRepository;
  private final EstimateMySqlQueryService service;

  @Test
  void fetchByIdReturnsEstimateFromTables() {
    var estimateId = new EstimateId("est_123e4567-e89b-12d3-a456-426614174000");

    var estimate = service.fetchById(estimateId);
    assertTrue(estimate.isPresent());

    estimate.ifPresent(fetched -> {
      assertEquals(estimateId, fetched.getEstimateId());
      assertEquals(new CustomerId("customer-001"), fetched.getCustomerId());
      assertEquals(new Subject("見積件名"), fetched.getSubject());
      assertEquals(new Memo("メモ"), fetched.getMemo());

      var lines = fetched.getLines();
      assertEquals(2, lines.lines().size());
    });
  }

  @Test
  void fetchByIdReturnsEmptyWhenEstimateDoesNotExist() {
    var estimateId = new EstimateId("est_123e4567-e89b-12d3-a456-000000000000");
    var estimate = service.fetchById(estimateId);

    assertTrue(estimate.isEmpty());
  }
}
