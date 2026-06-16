package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.EstimateStab;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.dto.entity.EstimateLineId;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.DraftEstimateJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateContextJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateLineJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateLinesJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"mysql"})
class EstimateMySqlStrategyTest {

  @Autowired
  private EstimateMySqlStrategy strategy;

  @Autowired
  private EstimateJpaRepository estimateJpaRepository;

  @Autowired
  private DraftEstimateJpaRepository draftEstimateJpaRepository;

  @Autowired
  private EstimateContextJpaRepository estimateContextJpaRepository;

  @Autowired
  private EstimateLinesJpaRepository estimateLinesJpaRepository;

  @Autowired
  private EstimateLineJpaRepository estimateLineJpaRepository;

  @Autowired
  private EstimateMySqlQueryService queryService;

  @Test
  void savePersistsEstimateToAllTables() {
    // Strategy 経由で保存し、Repository で実際に永続化された行を検証する。
    strategy.save(EstimateStab.create());

    assertTrue(estimateJpaRepository.existsById(EstimateStab.ESTIMATE_ID));
    assertTrue(draftEstimateJpaRepository.existsById(EstimateStab.ESTIMATE_ID));
    assertTrue(estimateContextJpaRepository.existsById(EstimateStab.ESTIMATE_ID));
    assertTrue(estimateLinesJpaRepository.existsById(EstimateStab.ESTIMATE_ID));
    assertTrue(estimateLineJpaRepository.existsById(new EstimateLineId(EstimateStab.ESTIMATE_ID, 1)));

    var context = estimateContextJpaRepository.findById(EstimateStab.ESTIMATE_ID).orElseThrow();
    assertEquals(EstimateStab.CUSTOMER_ID, context.getCustomerId());
    assertEquals(EstimateStab.SUBJECT, context.getSubject());
    assertEquals(EstimateStab.MEMO, context.getMemo());

    var lines = estimateLinesJpaRepository.findById(EstimateStab.ESTIMATE_ID).orElseThrow();
    assertEquals("excluded", lines.getLine_tax_class_policy());

    var line = estimateLineJpaRepository.findById(new EstimateLineId(EstimateStab.ESTIMATE_ID, 1)).orElseThrow();
    assertEquals("test", line.getDescription());
    assertEquals("excluded", line.getTaxClass());

    var fetched = queryService.fetchById(EstimateStab.create().getEstimateId()).orElseThrow();
    assertEquals(EstimateStab.CUSTOMER_ID, fetched.getCustomerId().customerId());
    assertEquals(EstimateStab.SUBJECT, fetched.getSubject().subject());
    assertEquals(EstimateStab.MEMO, fetched.getMemo().memo());
    assertEquals(2, fetched.getLines().lines().size());
  }
}
