package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.az.application.adapter.out.output.HttpError;

class CustomerIdTest {

  @Test
  void trim() {
    var customerId = new CustomerId(" customer-001 ");

    assertEquals("customer-001", customerId.customerId());
  }

  @Test
  void allow100Characters() {
    var value = "a".repeat(100);

    var customerId = new CustomerId(value);

    assertEquals(value, customerId.customerId());
  }

  @Test
  void rejectMoreThan100Characters() {
    var value = "a".repeat(101);

    var error = assertThrows(HttpError.class, () -> new CustomerId(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("顧客IDは、100文字以内である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"customer\n001", "customer\t001", "customer\u007f001"})
  void rejectControlCharacters(String value) {
    var error = assertThrows(HttpError.class, () -> new CustomerId(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("顧客IDには、改行、タブ、その他の制御文字は使用はできません。", error.getMessage());
  }
}
