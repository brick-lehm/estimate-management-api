package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import project.az.application.adapter.out.output.HttpError;

/**
 * 明細番号
 * @param lineNo
 */
public record LineNo(int lineNo) {

  public LineNo {
    if (lineNo < 1) {
      throw new HttpError(400, "明細番号は、正の数である必要があります。");
    }
  }
}
