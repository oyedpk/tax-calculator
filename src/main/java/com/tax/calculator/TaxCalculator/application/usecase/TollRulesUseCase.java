package com.tax.calculator.TaxCalculator.application.usecase;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TollRulesUseCase {

  public Float getTollFee(LocalTime time);

  public boolean isTollFreeDate(LocalDate localDate);

  public boolean isTollFreeVehicle(String vehicle);

  public void refreshRules();
}
