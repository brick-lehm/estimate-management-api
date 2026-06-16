package dev.cost.management.estimatemanagementapi.domain.models.estimate.value;

import static java.time.LocalDateTime.of;

import java.time.LocalDateTime;
import project.az.domain.model.value.datetime.Datetime;

/**
 * 見積もりの有効期限
 *
 */
public class ExpirationDate extends Datetime {
  /*
  1999年01月01日 0h 0min 0sec の場合は、未決定の有効期限として扱われます。
   */

  /**
   * 有効期限は、日付までの扱いであるため時・分・秒は常に0に設定する。
   */
  public ExpirationDate(LocalDateTime localDatetime) {
    super(
        (
            localDatetime
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
        )
    );
  }

  public static ExpirationDate notYetDecided() {
    var localDatetime = of(1999, 1, 1, 0, 0, 0);
    return new ExpirationDate(localDatetime);
  }
}
