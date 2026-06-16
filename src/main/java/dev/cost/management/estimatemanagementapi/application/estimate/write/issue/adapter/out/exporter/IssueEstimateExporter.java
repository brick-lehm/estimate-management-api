package dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.out.exporter;

import dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.out.result.IssueEstimateResult;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;

/**
 * 見積発行ユースケースに関する外部出力を生成する操作を提供します
 */
public interface IssueEstimateExporter {

  /**
   * 発行された見積を外部出力に変換する
   * @param issuedEstimate 発行された見積
   */
  void issuedEstimate(Estimate issuedEstimate);

  /**
   * 発行結果を返却する
   */
  IssueEstimateResult export();
}
