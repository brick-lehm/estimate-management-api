package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import project.az.application.adapter.out.output.HttpError;

/**
 * 説明
 */
public record Description(String description) {

  /**
   * - 保存される明細行では必須とする。
   * - 最大 200 文字とする。
   * - 前後の空白は保存前に除去する。除去後に空文字になる場合は不可とする。
   * - 改行、タブ、その他の制御文字は使用不可とする。
   * - 使用不可文字の検出正規表現は `[\x00-\x1F\x7F-\x9F]` とする。該当する文字が 1 文字でも含まれる場合は不可とする。
   * - 正規表現で Unicode 文字プロパティが使える実行環境では、同等の検出正規表現として `\p{Cc}` を使用してもよい。
   * - 通常の日本語、英数字、空白、記号は使用できる。`/`、`-`、`_`、`()`、`[]`、`&`、`#`、`.`、`,`、`:` など、サービス名、期間、数量単位、品番で使われる記号は許可する。
   */
  public Description {
    description = description.trim();
    if (description.isEmpty()) {
      throw new HttpError(400, "説明は必須です。");
    }

    if (description.length() > 200) {
      throw new HttpError(400, "説明は、200文字以内である必要があります。");
    }

    if (description.matches("(?s).*[\\x00-\\x1F\\x7F-\\x9F].*")) {
      throw new HttpError(400, "説明には、改行、タブ、その他の制御文字は使用はできません。");
    }
  }
}
