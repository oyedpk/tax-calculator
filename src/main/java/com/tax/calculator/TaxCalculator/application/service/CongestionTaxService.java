package com.tax.calculator.TaxCalculator.application.service;

import com.tax.calculator.TaxCalculator.adaptor.outbound.repository.TollRulesOutAdaptor;
import com.tax.calculator.TaxCalculator.application.usecase.CongestionTaxUseCase;
import com.tax.calculator.TaxCalculator.application.usecase.TollRulesUseCase;
import com.tax.calculator.TaxCalculator.domain.TollFeeForTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CongestionTaxService implements CongestionTaxUseCase, TollRulesUseCase {

  private Set<String> vehicles;
  private Set<LocalDate> tollFreeDates;
  private Set<TollFeeForTime> tollFeeForTimes;

  TollRulesOutAdaptor tollRulesOutAdaptor;

  @Autowired
  public CongestionTaxService(TollRulesOutAdaptor tollRulesOutAdaptor) {
    this.tollRulesOutAdaptor = tollRulesOutAdaptor;
  }

  @Override
  public Float calculateCongestionTax(String vehicle, List<LocalDateTime> dates) {
    if (Objects.isNull(vehicles)) {
      refreshRules();
    }

    List<LocalDateTime> sortedAndFilteredDates =
        dates.stream()
            .filter(Predicate.not(date -> isTollFreeDate(date.toLocalDate())))
            .sorted()
            .collect(Collectors.toUnmodifiableList());

    if (sortedAndFilteredDates.isEmpty() || isTollFreeVehicle(vehicle)) {
      return Float.valueOf(0);
    }

    LocalDateTime start = sortedAndFilteredDates.get(0);
    float totalFee = 0;

    for (LocalDateTime date : sortedAndFilteredDates) {
      float nextFee = getTollFee(date.toLocalTime());
      float tempFee = getTollFee(start.toLocalTime());

      long minutes = ChronoUnit.MINUTES.between(start, date);

      if (minutes <= 60) {
        if (totalFee > 0) totalFee -= tempFee;
        if (nextFee >= tempFee) tempFee = nextFee;
        totalFee += tempFee;
      } else {
        totalFee += nextFee;
        start = date;
      }
    }

    if (totalFee > 60) totalFee = 60;
    return totalFee;
  }

  @Override
  public void refreshRules() {
    vehicles = tollRulesOutAdaptor.getTollFreeVehicles();
    tollFreeDates = tollRulesOutAdaptor.getTollFreeDates();
    tollFeeForTimes = tollRulesOutAdaptor.getTollFees();
  }

  @Override
  public Float getTollFee(LocalTime time) {
    for (TollFeeForTime t : tollFeeForTimes) {
      if ((t.getStartTime().isBefore(time) && t.getEndTime().isAfter(time))
          || t.getStartTime().equals(time)
          || t.getEndTime().equals(time)) {
        return t.getFee();
      }
    }
    return Float.valueOf(0);
  }

  @Override
  public boolean isTollFreeDate(LocalDate localDate) {
    return DayOfWeek.SATURDAY.equals(localDate.getDayOfWeek())
        || DayOfWeek.SUNDAY.equals(localDate.getDayOfWeek())
        || tollFreeDates.contains(localDate);
  }

  @Override
  public boolean isTollFreeVehicle(String vehicle) {
    return vehicles.contains(vehicle);
  }
}
