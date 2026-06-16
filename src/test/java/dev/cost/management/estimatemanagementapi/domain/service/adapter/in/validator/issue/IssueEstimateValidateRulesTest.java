package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.issue;

import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.rules.EstimateValidateRule;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import project.az.application.adapter.out.output.HttpError;

class IssueEstimateValidateRulesTest {

  @Test
  void returnEmptyWhenNoRuleRegistered() {
    // ルール未登録の型は、検証対象外としてエラーにしない。
    var rules = new IssueEstimateValidateRules();

    var result = rules.validates("未登録の入力値");

    assertTrue(result.isEmpty());
  }

  @Test
  void returnEmptyWhenAllRulesPass() {
    // 対象型に紐づく全てのルールが通過した場合はエラーにしない。
    var rules = new IssueEstimateValidateRules();
    rules.put(String.class, List.of(passRule(), passRule()));

    var result = rules.validates("検証対象");

    assertTrue(result.isEmpty());
  }

  @Test
  void returnFirstError() {
    // 複数ルールのうち最初に検出されたエラーを返す。
    var rules = new IssueEstimateValidateRules();
    rules.put(String.class, List.of(
        passRule(),
        errorRule(new HttpError(400, "最初のエラー")),
        errorRule(new HttpError(400, "後続のエラー"))
    ));

    var result = rules.validates("検証対象");

    assertTrue(result.isPresent());
    assertEquals(400, result.get().getStatusCode());
    assertEquals("最初のエラー", result.get().getMessage());
  }

  private EstimateValidateRule passRule() {
    return new EstimateValidateRule() {
      @Override
      public Optional<HttpError> validate(Object validObject) {
        return Optional.empty();
      }
    };
  }

  private EstimateValidateRule errorRule(HttpError error) {
    return new EstimateValidateRule() {
      @Override
      public Optional<HttpError> validate(Object validObject) {
        return Optional.of(error);
      }
    };
  }
}
