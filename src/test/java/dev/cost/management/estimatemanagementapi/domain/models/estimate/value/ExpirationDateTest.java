package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExpirationDateTest {

  @Test
  void truncateTimeToStartOfDay() {
    // 有効期限は日付だけを扱うため、入力された時・分・秒・ナノ秒は 0 に丸める。
    var expirationDate = new ExpirationDate(of(2026, 5, 11, 12, 34, 56, 789));

    assertEquals(of(2026, 5, 11, 0, 0, 0, 0), expirationDate.getCurrent());
  }

  @Test
  void notYetDecided() {
    // 未決定の有効期限は、固定値の 1999-01-01 00:00:00 として扱う。
    var expirationDate = ExpirationDate.notYetDecided();

    assertEquals(of(1999, 1, 1, 0, 0, 0, 0), expirationDate.getCurrent());
  }
}
