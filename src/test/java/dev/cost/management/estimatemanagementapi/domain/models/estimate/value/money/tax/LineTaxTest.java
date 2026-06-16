package dev.cost.management.estimatemanagementapi.domain.models.estimate.value.money.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LineTaxTest {

  @Test
  void round() {
    // 明細単位の税額は四捨五入して整数相当の金額に丸める。
    var lineTax = new LineTax(10.5);

    assertEquals(new LineTax(11.0), lineTax.round());
  }

  @Test
  void sum() {
    // 明細単位の税額同士を合算する。
    var lineTax = new LineTax(1000.0);

    var result = lineTax.sum(new LineTax(250.0));

    assertEquals(new LineTax(1250.0), result);
  }
}
