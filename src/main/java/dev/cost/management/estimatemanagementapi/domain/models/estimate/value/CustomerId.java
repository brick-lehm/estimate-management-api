package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import project.az.application.adapter.out.output.HttpError;

/**
 * 顧客識別子
 * @param customerId
 */
public record CustomerId(String customerId) {

  /**
   * 常に必須である必要はない。
   * 見積もりの状態や操作に応じてバリデーション内容が変化する。
   * 共通する最低限のバリデーションのみ実装します。
   * 顧客IDは、外部システムが採番したIDを使う想定外あるので記号のバリデーションを緩めにしています。
   * ---
   * - 提出時は必須とする
   * - 最大 100 文字とする。。
   * - 前後の空白は保存前に除去する。除去後に空文字になる場合は不可とする。
   * - 改行、タブ、その他の制御文字は使用不可とする。
   * - 使用不可文字の検出正規表現は `[\x00-\x1F\x7F-\x9F]` とする。該当する文字が 1 文字でも含まれる場合は不可とする。
   */
  public CustomerId {
    customerId = customerId.trim();
    if (customerId.length() > 100) {
      throw new HttpError(400, "顧客IDは、100文字以内である必要があります。");
    }

    if (customerId.matches("(?s).*[\\x00-\\x1F\\x7F-\\x9F].*")) {
      throw new HttpError(400, "顧客IDには、改行、タブ、その他の制御文字は使用はできません。");
    }
  }
}
