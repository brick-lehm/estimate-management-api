package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input;

import static java.util.Arrays.stream;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.CustomerId;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Memo;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.az.application.adapter.out.output.HttpError;

/**
 * 発行する見積もり内容
 */
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class IssueEstimateContext {

  private ExpirationDate expirationDate;
  private CustomerId customerId;
  private Subject subject;
  private Memo memo;
  private LineTaxClassPolicy lineTaxClassPolicy;
  private EditLineActions lines;

  /**
   * null以外の見積内容を返却する
   */
  public List<Object> properties() {

    return stream(getClass().getDeclaredFields()).reduce(
        new ArrayList<>(),
        (before, next) -> {

          try {

            next.setAccessible(true);
            var filedObject = next.get(this);

            if (filedObject != null) {
              before.add(filedObject);
            }
            return before;
          } catch (Exception e) {
            throw new HttpError(500, "不明なエラーが発生しました");
          }

        },
        (a, b) -> {
          a.addAll( b );
          return a;
        }
    );

  }
}
