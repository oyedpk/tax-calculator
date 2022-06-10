package com.tax.calculator.TaxCalculator.infrastructure.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongestionTaxDto {

  String vehicle;

  List<String> dates;
}
