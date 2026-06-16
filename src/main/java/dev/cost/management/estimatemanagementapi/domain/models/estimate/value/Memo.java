package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import project.az.application.adapter.out.output.HttpError;

/**
 * メモ
 * @param memo
 */
public record Memo(String memo) {

  /**
   * ## メモ
   * - 最大 2,000 文字とする。
   * - 空白と改行だけの場合は未入力として扱う。
   * - 改行は使用できる。保存前に改行コードは `LF` に正規化する。
   * - 改行以外の制御文字は使用不可とする。
   * - 改行コード正規化後の使用不可文字の検出正規表現は `[\x00-\x09\x0B-\x1F\x7F-\x9F]` とする。
   *   該当する文字が 1 文字でも含まれる場合は不可とする。
   * - 通常の日本語、英数字、空白、記号は使用できる。
   */
  public Memo {
    memo = memo.replace("\r\n", "\n").replace("\r", "\n");
    memo = memo.trim();

    if (memo.length() > 2000) {
      throw new HttpError(400, "メモは、最大で2,000文字である必要があります。");
    }

    if (memo.matches("(?s).*[\\x00-\\x09\\x0B-\\x1F\\x7F-\\x9F].*")) {
      throw new HttpError(400, "メモに制御文字は使用できません。");
    }
  }
}
