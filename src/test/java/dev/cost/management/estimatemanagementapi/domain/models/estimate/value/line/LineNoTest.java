package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import project.az.application.adapter.out.output.HttpError;

class LineNoTest {

  @Test
  void allowPositiveNumber() {
    // 明細番号は 1 以上の正の数を許可する。
    var lineNo = new LineNo(1);

    assertEquals(1, lineNo.lineNo());
  }

  @Test
  void rejectZero() {
    // 明細番号が 0 の場合は正の数ではないため許可しない。
    var error = assertThrows(HttpError.class, () -> new LineNo(0));

    assertEquals(400, error.getStatusCode());
    assertEquals("明細番号は、正の数である必要があります。", error.getMessage());
  }

  @Test
  void rejectNegative() {
    // 明細番号が負の値の場合は許可しない。
    var error = assertThrows(HttpError.class, () -> new LineNo(-1));

    assertEquals(400, error.getStatusCode());
    assertEquals("明細番号は、正の数である必要があります。", error.getMessage());
  }
}
