package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import project.az.application.adapter.out.output.HttpError;

/**
 * VO
 * @param estimateId 見積もりID
 */
public record EstimateId(String estimateId) {

  private static final String UUID_PATTERN =
      "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

  /**
   * 常に必須である。
   * est_ で始まること
   * est_{uuid} で構成されていること
   * UUIDで利用可能な文字を許容する
   * ---
   * - 最大 100 文字とする。
   * - 前後の空白は保存前に除去する。除去後に空文字になる場合は不可とする。
   * - 改行、タブ、その他の制御文字は使用不可とする。
   * - 使用不可文字の検出正規表現は `[\x00-\x1F\x7F-\x9F]` とする。該当する文字が 1 文字でも含まれる場合は不可とする。
   */
  public EstimateId {
    estimateId = estimateId.trim();
    if (estimateId.isEmpty()) {
      throw new HttpError(400, "見積もりIDは必須です。");
    }

    if (estimateId.length() > 100) {
      throw new HttpError(400, "見積もりIDは、100文字以内である必要があります。");
    }

    if (estimateId.matches("(?s).*[\\x00-\\x1F\\x7F-\\x9F].*")) {
      throw new HttpError(400, "見積もりIDには、改行、タブ、その他の制御文字は使用はできません。");
    }

    if (!estimateId.matches("est_" + UUID_PATTERN)) {
      throw new HttpError(400, "見積もりIDは、est_{uuid}形式である必要があります。");
    }
  }
}
