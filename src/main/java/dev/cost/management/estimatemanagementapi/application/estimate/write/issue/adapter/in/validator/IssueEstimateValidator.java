package dev.cost.management.estimatemanagementapi.application.estimate.write.issue.adapter.in.validator;

/**
 * 見積発行ユースケースに関する外部入力をバリデーションする操作を提供します
 */
public interface IssueEstimateValidator<T> {

  void validate(T input);
}
