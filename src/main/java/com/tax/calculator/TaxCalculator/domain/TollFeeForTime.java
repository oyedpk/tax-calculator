package com.tax.calculator.TaxCalculator.domain;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TollFeeForTime {
  private LocalTime startTime;
  private LocalTime endTime;
  private Float fee;
}
