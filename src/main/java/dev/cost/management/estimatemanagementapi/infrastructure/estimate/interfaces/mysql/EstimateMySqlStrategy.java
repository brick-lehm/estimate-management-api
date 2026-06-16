package dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.EstimateOutportStrategy;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.mapper.EstimateRecordMapper;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.DraftEstimateJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateContextJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateLineJpaRepository;
import dev.cost.management.estimatemanagementapi.infrastructure.estimate.interfaces.mysql.repository.EstimateLinesJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Profile: default または、mysql
 * Mysqlを使った永続化処理を提供します
 */
@Component
@Profile({"mysql", "default"})
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EstimateMySqlStrategy implements EstimateOutportStrategy {

  private final EstimateRecordMapper estimateRecordMapper;

  private final EstimateJpaRepository estimateJpaRepository;
  private final DraftEstimateJpaRepository draftEstimateJpaRepository;
  private final EstimateContextJpaRepository estimateContextJpaRepository;
  private final EstimateLinesJpaRepository estimateLinesJpaRepository;
  private final EstimateLineJpaRepository estimateLineJpaRepository;

  /**
   * mysqlに永続化します。
   * テーブル定義: mysql/migration/0_estimate_table.sql
   * 見積の新規作成が対象となるメソッドであるため、判定なしで下書きテーブルに見積IDを登録します。
   */
  @Override
  @Transactional
  public void save(Estimate estimate) {

    var saveEstimateRecord = estimateRecordMapper.toWriteConvert(estimate);

    estimateJpaRepository.save( saveEstimateRecord.estimate() );

    draftEstimateJpaRepository.save( saveEstimateRecord.stateEstimate() );

    estimateContextJpaRepository.save( saveEstimateRecord.estimateContext() );

    estimateLinesJpaRepository.save( saveEstimateRecord.lines() );
    estimateLineJpaRepository.saveAll( saveEstimateRecord.line() );
    estimateLineJpaRepository.flush();
  }
}
