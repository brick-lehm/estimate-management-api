package dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.ExpirationDate;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.validator.rules.ValidationExpirationDate;
import org.junit.jupiter.api.Test;

class ValidationExpirationDateTest {

  private final ValidationExpirationDate validation = new ValidationExpirationDate();

  @Test
  void allowToday() {
    // 有効期限当日は期限切れではないため、バリデーションエラーにしない。
    var expirationDate = new ExpirationDate(now().atStartOfDay());

    var result = validation.validate(expirationDate);

    assertTrue(result.isEmpty());
  }

  @Test
  void allowFutureDate() {
    // 有効期限が翌日以降の場合は有効な日付として扱う。
    var expirationDate = new ExpirationDate(now().plusDays(1).atStartOfDay());

    var result = validation.validate(expirationDate);

    assertTrue(result.isEmpty());
  }

  @Test
  void rejectPastDate() {
    // 有効期限が前日以前の場合は期限切れとしてエラーにする。
    var expirationDate = new ExpirationDate(now().minusDays(1).atStartOfDay());

    var result = validation.validate(expirationDate);

    assertTrue(result.isPresent());
    assertEquals(400, result.get().getStatusCode());
    assertEquals("有効期限は、当日以降の日付である必要があります。", result.get().getMessage());
  }

  @Test
  void ignoreUnsupportedObject() {
    // ExpirationDate 以外の値は、このルールの検証対象外として無視する。
    var result = validation.validate("2026-05-11");

    assertTrue(result.isEmpty());
  }
}
