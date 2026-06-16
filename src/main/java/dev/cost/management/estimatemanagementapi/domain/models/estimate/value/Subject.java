package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import project.az.application.adapter.out.output.HttpError;

/**
 * 件名
 * @param subject
 */
public record Subject(String subject) {

  /**
   * 常に必須である必要はない。
   * 見積もりの状態や操作に応じてバリデーション内容が変化する。
   * 共通する最低限のバリデーションのみ実装します。
   * ---
   * - 最大 100 文字とする。
   * - 前後の空白は保存前に除去する。
   * - 除去後に空文字になる場合は不可とする。
   * - 改行、タブ、その他の制御文字は使用不可とする。
   * - 使用不可文字の検出正規表現は `[\x00-\x1F\x7F-\x9F]` とする。該当する文字が 1 文字でも含まれる場合は不可とする。
   * - 正規表現で Unicode 文字プロパティが使える実行環境では、同等の検出正規表現として `\p{Cc}` を使用してもよい。
   * - 通常の日本語、英数字、空白、記号は使用（`/`、`-`、`_`、`()`、`[]`、`&`、`#`）できる。
   */
  public Subject {
    subject = subject.trim();

    if (subject.length() > 100) {
      throw new HttpError(400, "件名は、100文字以内である必要があります。");
    }

    if (subject.matches("(?s).*[\\x00-\\x1F\\x7F-\\x9F].*")) {
      throw new HttpError(400, "件名には、改行、タブ、その他の制御文字は使用はできません。");
    }

    if (subject.matches(".*[\\p{P}\\p{S}&&[^/\\-_()\\[\\]&#]].*")) {
      throw new HttpError(400, "件名には、`/`、`-`、`_`、`()`、`[]`、`&`、`#`以外の記号は使用できません。");
    }
  }
}
