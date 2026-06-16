package dev.cost.management.estimatemanagementapi.domain.service.factory;

import static dev.cost.management.estimatemanagementapi.domain.models.estimate.value.status.EstimateStatus.Draft;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.root.Estimate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.EstimateId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line.Lines;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.AddLineAction;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.EditLineActions;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * ファクトリ: 下書きの見積もりを発行
 */
@Component("IssueDraftEstimate")
public class IssueDraftEstimate {

  private EstimateId assignEstimateId() {
    UUID uuid = UUID.randomUUID();
    return new EstimateId( "est_%s".formatted( uuid ) );
  }

  /**
   * ## 有効期限
   * - 下書き時は、必須ではないためnullである場合は未指定を返却する
   */
  private ExpirationDate expirationDate(ExpirationDate expirationDate) {
    if (expirationDate == null) return ExpirationDate.notYetDecided();
    return expirationDate;
  }

  /**
   * ## 顧客ID
   * - 下書き時は、必須ではないためnullの場合は空の顧客IDを返却する
   */
  private CustomerId customerId(CustomerId customerId) {
    if (customerId == null) return new CustomerId("");
    return customerId;
  }

  /**
   * ## 件名
   * - 下書き時は、必須ではないためnullの場合は空の件名を返却する
   */
  private Subject subject(Subject subject) {
    if (subject == null) return new Subject("");
    return subject;
  }

  /**
   * ## メモ
   * 必須ではない。
   */
  private Memo memo(Memo memo) {
    if (memo == null) return new Memo("");
    return memo;
  }

  /**
   * ## 明細
   * - 下書きの見積もりを作成するときは、追加コマンドのみを実行する
   */
  private Lines line(EditLineActions editLineActions, LineTaxClassPolicy lineTaxClassPolicy) {
    var lines = new Lines(lineTaxClassPolicy);
    var addLineActions = editLineActions.extractActions(AddLineAction.class);

    return Draft.editLine(lines, addLineActions);
  }

  /**
   * 見積を発行する
   * 明細は、すでに見積もりクラスに対応する便利なメソッドがあるので見積もりクラスのメソッドを通して実行します。
   */
  public Estimate issueEstimate(IssueEstimateContext issueEstimateContext) {

    return new Estimate(
        assignEstimateId(),
        customerId(issueEstimateContext.getCustomerId()),
        expirationDate( issueEstimateContext.getExpirationDate() ),
        Draft,
        subject(issueEstimateContext.getSubject()),
        memo(issueEstimateContext.getMemo()),
        line(
            issueEstimateContext.getLines(),
            issueEstimateContext.getLineTaxClassPolicy()
        )
    );
  }
}
