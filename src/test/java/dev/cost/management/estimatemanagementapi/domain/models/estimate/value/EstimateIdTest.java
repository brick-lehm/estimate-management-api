package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.az.application.adapter.out.output.HttpError;

class EstimateIdTest {

  @Test
  void trim() {
    // 保存前に前後の空白を除去し、除去後の値を見積もりIDとして保持する。
    var estimateId = new EstimateId(" est_123e4567-e89b-12d3-a456-426614174000 ");

    assertEquals("est_123e4567-e89b-12d3-a456-426614174000", estimateId.estimateId());
  }

  @Test
  void allowEstUuidFormat() {
    // est_ で始まり、その後ろが UUID 形式で構成されている値を許可する。
    var value = "est_123e4567-e89b-12d3-a456-426614174000";

    var estimateId = new EstimateId(value);

    assertEquals(value, estimateId.estimateId());
  }

  @Test
  void rejectBlank() {
    // 見積もりIDは必須であるため、空白を除去した後に空文字になる値は許可しない。
    var error = assertThrows(HttpError.class, () -> new EstimateId(" "));

    assertEquals(400, error.getStatusCode());
    assertEquals("見積もりIDは必須です。", error.getMessage());
  }

  @Test
  void rejectMoreThan100Characters() {
    // 見積もりIDは最大 100 文字までとし、それを超える値は許可しない。
    var value = "est_" + "a".repeat(97);

    var error = assertThrows(HttpError.class, () -> new EstimateId(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("見積もりIDは、100文字以内である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "est_123e4567-e89b-12d3-a456\n-426614174000",
      "est_123e4567-e89b-12d3-a456\t-426614174000",
      "est_123e4567-e89b-12d3-a456\u007f-426614174000"
  })
  void rejectControlCharacters(String value) {
    // 改行、タブ、その他の制御文字が含まれる値は許可しない。
    var error = assertThrows(HttpError.class, () -> new EstimateId(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("見積もりIDには、改行、タブ、その他の制御文字は使用はできません。", error.getMessage());
  }

  @Test
  void rejectWithoutEstPrefix() {
    // est_ で始まらない値は、見積もりIDの形式として許可しない。
    var error = assertThrows(
        HttpError.class,
        () -> new EstimateId("123e4567-e89b-12d3-a456-426614174000")
    );

    assertEquals(400, error.getStatusCode());
    assertEquals("見積もりIDは、est_{uuid}形式である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "est_123e4567e89b12d3a456426614174000",
      "est_123e4567-e89b-12d3-a456-42661417400z",
      "est_not-uuid"
  })
  void rejectInvalidUuidFormat(String value) {
    // est_ の後ろが UUID 形式ではない値は許可しない。
    var error = assertThrows(HttpError.class, () -> new EstimateId(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("見積もりIDは、est_{uuid}形式である必要があります。", error.getMessage());
  }
}
