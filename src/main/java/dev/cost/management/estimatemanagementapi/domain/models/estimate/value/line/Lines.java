package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.line;

import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineSubtotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.amount.LineTotal;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.LineTax;
import dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax.policy.LineTaxClassPolicy;
import dev.cost.management.estimatemanagementapi.domain.service.adapter.in.input.AddLineAction;
import java.util.ArrayList;
import java.util.List;
import project.az.application.adapter.out.output.HttpError;

/**
 * 明細
 */
public record Lines(List<Line> lines, LineTaxClassPolicy lineTaxClassPolicy) {

  public Lines(LineTaxClassPolicy lineTaxClassPolicy) {
    this(new ArrayList<>(), lineTaxClassPolicy);
  }

  public Lines {
    if (lines.size() > 100) {
      throw new HttpError(400, "明細は、100行以内である必要があります。");
    }

    lineTaxClassPolicy.policyCheck(lines);
  }

  public Lines addLine(AddLineAction addLineAction) {
    var addedLines = new ArrayList<>(lines);
    addedLines.add( new Line(
            new LineNo( addedLines.size() + 1),
            addLineAction.getDescription(),
            addLineAction.getQuantity(),
            addLineAction.getUnitPrice()
    ) );
    return new Lines(addedLines, lineTaxClassPolicy);
  }

  // ------------------------------------------------------------------------

  /**
   * 見積もり単位.小計
   */
  public LineSubtotal lineSubtotal() {
    return lines.stream().reduce(
        new LineSubtotal(0.0),
        (subtotal, nextLine) -> subtotal.sum( nextLine.lineSubtotal() ),
        LineSubtotal::sum
    );
  }

  /**
   * 見積もり単位.税額
   */
  public LineTax lineTax() {
    return lines.stream().reduce(
        new LineTax(0.0),
        (subtotal, nextLine) -> subtotal.sum( nextLine.lineTax() ),
        LineTax::sum
    );
  }

  /**
   * 見積もり単位.合計
   */
  public LineTotal lineTotal() {
    return lines.stream().reduce(
        new LineTotal(0.0),
        (subtotal, nextLine) -> subtotal.sum( nextLine.lineTotal() ),
        LineTotal::sum
    );
  }
}
