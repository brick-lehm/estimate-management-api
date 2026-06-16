package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.az.application.adapter.out.output.HttpError;

class DescriptionTest {

  @Test
  void trim() {
    // 保存前に前後の空白を除去し、除去後の値を説明として保持する。
    var description = new Description(" 作業内容 ");

    assertEquals("作業内容", description.description());
  }

  @Test
  void rejectBlank() {
    // 保存される明細行では説明は必須であるため、空白だけの値は許可しない。
    var error = assertThrows(HttpError.class, () -> new Description(" "));

    assertEquals(400, error.getStatusCode());
    assertEquals("説明は必須です。", error.getMessage());
  }

  @Test
  void allow200Characters() {
    // 説明は最大 200 文字まで許可する。
    var value = "a".repeat(200);

    var description = new Description(value);

    assertEquals(value, description.description());
  }

  @Test
  void rejectMoreThan200Characters() {
    // 説明が 200 文字を超える場合は許可しない。
    var value = "a".repeat(201);

    var error = assertThrows(HttpError.class, () -> new Description(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("説明は、200文字以内である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"作業\n内容", "作業\t内容", "作業\u007f内容"})
  void rejectControlCharacters(String value) {
    // 改行、タブ、その他の制御文字が含まれる値は許可しない。
    var error = assertThrows(HttpError.class, () -> new Description(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("説明には、改行、タブ、その他の制御文字は使用はできません。", error.getMessage());
  }

  @Test
  void allowSymbols() {
    // サービス名、期間、数量単位、品番で使われる一般的な記号は説明で使用できる。
    var value = "開発/保守-API_改修(5月)[品番A]&追加#1.2,3:4";

    var description = new Description(value);

    assertEquals(value, description.description());
  }
}
