package com.tax.calculator.TaxCalculator.adaptor.outbound.repository;

import com.tax.calculator.TaxCalculator.domain.TollFeeForTime;
import com.tax.calculator.TaxCalculator.infrastructure.repository.TollFeeIntervalRepository;
import com.tax.calculator.TaxCalculator.infrastructure.repository.TollFreeDatesRepository;
import com.tax.calculator.TaxCalculator.infrastructure.repository.TollFreeVehicleRepository;
import com.tax.calculator.TaxCalculator.infrastructure.repository.entity.FeeIntervals;
import com.tax.calculator.TaxCalculator.infrastructure.repository.entity.TollFreeDates;
import com.tax.calculator.TaxCalculator.infrastructure.repository.entity.TollFreeVehicle;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TollRulesOutAdaptor {

  private final TollFeeIntervalRepository tollFeeIntervalRepository;

  private final TollFreeDatesRepository tollFreeDatesRepository;

  private final TollFreeVehicleRepository tollFreeVehicleRepository;

  public Set<TollFeeForTime> getTollFees() {
    return tollFeeIntervalRepository.findAll().stream()
        .map(this::convert)
        .collect(Collectors.toUnmodifiableSet());
  }

  public Set<LocalDate> getTollFreeDates() {
    return tollFreeDatesRepository.findAll().stream()
        .map(TollFreeDates::getDate)
        .collect(Collectors.toUnmodifiableSet());
  }

  public Set<String> getTollFreeVehicles() {
    return tollFreeVehicleRepository.findAll().stream()
        .map(TollFreeVehicle::getVehicle)
        .collect(Collectors.toUnmodifiableSet());
  }

  private TollFeeForTime convert(FeeIntervals feeIntervals) {
    var fee = feeIntervals.getFee();
    var tollFeeInterval = feeIntervals.getTimeInterval();
    String[] token = tollFeeInterval.split("-");
    var startTime = token[0];
    var endTime = token[1];
    return new TollFeeForTime(LocalTime.parse(startTime), LocalTime.parse(endTime), fee);
  }
}
