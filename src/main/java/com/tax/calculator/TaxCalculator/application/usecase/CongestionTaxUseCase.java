package com.tax.calculator.TaxCalculator.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

public interface CongestionTaxUseCase {

  Float calculateCongestionTax(String vehicle, List<LocalDateTime> dates);

  void refreshRules();
}
