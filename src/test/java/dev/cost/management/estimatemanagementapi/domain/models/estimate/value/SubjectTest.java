package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.az.application.adapter.out.output.HttpError;

class SubjectTest {

  @Test
  void trim() {
    var subject = new Subject(" 見積件名 ");

    assertEquals("見積件名", subject.subject());
  }

  @Test
  void allow100Characters() {
    var value = "a".repeat(100);

    var subject = new Subject(value);

    assertEquals(value, subject.subject());
  }

  @Test
  void rejectMoreThan100Characters() {
    var value = "a".repeat(101);

    var error = assertThrows(HttpError.class, () -> new Subject(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("件名は、100文字以内である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"見積\n件名", "見積\t件名", "見積\u007f件名"})
  void rejectControlCharacters(String value) {
    var error = assertThrows(HttpError.class, () -> new Subject(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("件名には、改行、タブ、その他の制御文字は使用はできません。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"見積/件名", "見積-件名", "見積_件名", "見積(件名)", "見積[件名]", "見積&件名", "見積#件名"})
  void allowPermittedSymbols(String value) {
    var subject = new Subject(value);

    assertEquals(value, subject.subject());
  }

  @ParameterizedTest
  @ValueSource(strings = {"見積@件名", "見積!件名", "見積+件名", "見積=件名"})
  void rejectNotPermittedSymbols(String value) {
    var error = assertThrows(HttpError.class, () -> new Subject(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("件名には、`/`、`-`、`_`、`()`、`[]`、`&`、`#`以外の記号は使用できません。", error.getMessage());
  }
}
