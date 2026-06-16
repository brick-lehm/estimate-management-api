package dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.importer;

import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;

/**
 * 見積発行ユースケースに関する外部入力を変換する操作を提供します
 */
public interface IssueEstimateImporter<T> {

  IssueEstimateContext issueEstimateContext(T input);
}
