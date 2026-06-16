package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.issue;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.Subject;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.IssueEstimateContext;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class IssueEstimateValidatorTest {

  private final IssueEstimateValidator validator = new IssueEstimateValidator();

  @Test
  void returnEmptyWhenNoInputProperties() {
    // 下書きでは全項目必須ではないため、入力がない場合は検証エラーにしない。
    var context = new IssueEstimateContext();

    var result = validator.validate(context);

    assertTrue(result.isEmpty());
  }

  @Test
  void validateExpirationDate() {
    // 入力された有効期限に対して、発行時の有効期限ルールを適用する。
    var context = new IssueEstimateContext();
    setField(context, "expirationDate", new ExpirationDate(now().minusDays(1).atStartOfDay()));

    var result = validator.validate(context);

    assertTrue(result.isPresent());
    assertEquals(400, result.get().getStatusCode());
    assertEquals("有効期限は、当日以降の日付である必要があります。", result.get().getMessage());
  }

  @Test
  void ignorePropertyWithoutIssueRule() {
    // 有効期限以外の入力値は、発行バリデータにルールがない限り検証対象外として扱う。
    var context = new IssueEstimateContext();
    setField(context, "subject", new Subject("見積件名"));

    var result = validator.validate(context);

    assertTrue(result.isEmpty());
  }

  @Test
  void returnFirstErrorFromInputProperties() {
    // 複数の入力値があっても、最初に検出された発行バリデーションエラーを返す。
    var context = new IssueEstimateContext();
    setField(context, "expirationDate", new ExpirationDate(now().minusDays(1).atStartOfDay()));
    setField(context, "subject", new Subject("見積件名"));

    var result = validator.validate(context);

    assertTrue(result.isPresent());
    assertEquals("有効期限は、当日以降の日付である必要があります。", result.get().getMessage());
  }

  private void setField(IssueEstimateContext context, String fieldName, Object value) {
    try {
      Field field = IssueEstimateContext.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(context, value);
    } catch (ReflectiveOperationException e) {
      throw new AssertionError(e);
    }
  }
}
