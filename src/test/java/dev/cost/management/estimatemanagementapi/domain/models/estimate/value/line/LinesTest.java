package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import static org.junit.jupiter.api.Assertions.*;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.Amount;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineSubtotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineTotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.UnitPrice;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxClass;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.TaxRate;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.AddLineAction;
import java.util.List;
import org.junit.jupiter.api.Test;
import project.az.application.adapter.out.output.HttpError;

class LinesTest {

  @Test
  void lineTotal() {
    // 明細ごとの合計を合算し、見積もり単位の合計を求める。
    var line1 = LineStab.create(); // 合計 22,000
    var line2 = LineStab.create( new Amount(2500.0) ); // 合計 5,500
    var lines = new Lines(List.of(line1, line2), LineTaxClassPolicy.excluded);

    var lineTotal = lines.lineTotal();

    assertEquals(new LineTotal(27500.0), lineTotal);
  }

  @Test
  void lineTax() {
    // 明細ごとの税額を合算し、見積もり単位の税額を求める。
    var line1 = LineStab.create(); // 税額 2,000
    var line2 = LineStab.create( new Amount(2500.0) ); // 税額 500
    var lines = new Lines(List.of(line1, line2), LineTaxClassPolicy.excluded);

    var lineTax = lines.lineTax();

    assertEquals(new LineTax(2500.0), lineTax);
  }

  @Test
  void lineSubtotal() {
    // 明細ごとの小計を合算し、見積もり単位の小計を求める。
    var line1 = LineStab.create(); // 小計 20,000
    var line2 = LineStab.create( new Amount(2500.0) ); // 小計 5,000
    var lines = new Lines(List.of(line1, line2), LineTaxClassPolicy.excluded);

    var lineSubtotal = lines.lineSubtotal();

    assertEquals(new LineSubtotal(25000.0), lineSubtotal);
  }

  @Test
  void allow100Lines() {
    // 明細は 100 行まで作成できる。
    var values = java.util.stream.IntStream.rangeClosed(1, 100)
        .mapToObj(i -> LineStab.create())
        .toList();

    var lines = new Lines(values, LineTaxClassPolicy.excluded);

    assertEquals(100, lines.lines().size());
  }

  @Test
  void rejectMoreThan100Lines() {
    // 明細が 100 行を超える場合は許可しない。
    var values = java.util.stream.IntStream.rangeClosed(1, 101)
        .mapToObj(i -> LineStab.create())
        .toList();

    var error = assertThrows(
        HttpError.class,
        () -> new Lines(values, LineTaxClassPolicy.excluded)
    );

    assertEquals(400, error.getStatusCode());
    assertEquals("明細は、100行以内である必要があります。", error.getMessage());
  }

  @Test
  void addLineAssignsNextLineNo() {
    // 追加する明細には既存行数の次の明細番号を採番する。
    var lines = new Lines(List.of(LineStab.create()), LineTaxClassPolicy.excluded);
    var action = new AddLineAction(
        new Description("追加作業"),
        new Quantity(1.0),
        new UnitPrice(new Amount(500.0), new TaxRate(0.1), TaxClass.excluded)
    );

    var result = lines.addLine(action);

    assertEquals(2, result.lines().size());
    assertEquals(new LineNo(2), result.lines().get(1).lineNo());
  }

  @Test
  void rejectIncludedTaxLineWhenPolicyExcluded() {
    // 外税固定ポリシーでは内税の明細を許可しない。
    var values = List.of(LineStab.create(new Amount(11000.0), TaxClass.included));

    var error = assertThrows(
        HttpError.class,
        () -> new Lines(values, LineTaxClassPolicy.excluded)
    );

    assertEquals(400, error.getStatusCode());
    assertEquals("明細は、全て外税である必要があります。", error.getMessage());
  }

  @Test
  void rejectExcludedTaxLineWhenPolicyIncluded() {
    // 内税固定ポリシーでは外税の明細を許可しない。
    var values = List.of(LineStab.create(new Amount(10000.0), TaxClass.excluded));

    var error = assertThrows(
        HttpError.class,
        () -> new Lines(values, LineTaxClassPolicy.included)
    );

    assertEquals(400, error.getStatusCode());
    assertEquals("明細は、全て内税である必要があります。", error.getMessage());
  }
}
