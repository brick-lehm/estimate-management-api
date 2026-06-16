package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.az.application.adapter.out.output.HttpError;

class MemoTest {

  @Test
  void trim() {
    // 保存前に前後の空白を除去し、除去後の値をメモとして保持する。
    var memo = new Memo(" メモ本文 ");

    assertEquals("メモ本文", memo.memo());
  }

  @Test
  void allow2000Characters() {
    // メモは最大 2,000 文字まで許可する。
    var value = "a".repeat(2000);

    var memo = new Memo(value);

    assertEquals(value, memo.memo());
  }

  @Test
  void rejectMoreThan2000Characters() {
    // メモが 2,000 文字を超える場合は許可しない。
    var value = "a".repeat(2001);

    var error = assertThrows(HttpError.class, () -> new Memo(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("メモは、最大で2,000文字である必要があります。", error.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"   ", "\n", " \n \r\n "})
  void treatBlankAsEmpty(String value) {
    // 空白と改行だけのメモは、未入力として空文字で保持する。
    var memo = new Memo(value);

    assertEquals("", memo.memo());
  }

  @Test
  void normalizeLineBreaksToLf() {
    // CRLF と CR の改行コードは、保存前に LF に正規化する。
    var memo = new Memo("1行目\r\n2行目\r3行目");

    assertEquals("1行目\n2行目\n3行目", memo.memo());
  }

  @Test
  void allowLineFeed() {
    // LF の改行はメモ本文で使用できる。
    var value = "1行目\n2行目";

    var memo = new Memo(value);

    assertEquals(value, memo.memo());
  }

  @ParameterizedTest
  @ValueSource(strings = {"メモ\t本文", "メモ\u0000本文", "メモ\u007f本文"})
  void rejectControlCharactersExceptLineFeed(String value) {
    // LF 以外の制御文字が含まれる値は許可しない。
    var error = assertThrows(HttpError.class, () -> new Memo(value));

    assertEquals(400, error.getStatusCode());
    assertEquals("メモに制御文字は使用できません。", error.getMessage());
  }

  @Test
  void allowSymbols() {
    // 通常の日本語、英数字、空白、記号はメモ本文で使用できる。
    var value = "見積メモ ABC 123 /-_()[]&#!+=@";

    var memo = new Memo(value);

    assertEquals(value, memo.memo());
  }
}
