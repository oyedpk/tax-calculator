package com.tax.calculator.TaxCalculator.adaptor.inbound.http;

import com.tax.calculator.TaxCalculator.application.usecase.CongestionTaxUseCase;
import com.tax.calculator.TaxCalculator.infrastructure.controller.dto.CongestionTaxDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CongestionTaxInAdaptor {

  private final CongestionTaxUseCase congestionTaxUseCase;

  public Float calculateCongestionTax(CongestionTaxDto congestionTaxDto) {
    return congestionTaxUseCase.calculateCongestionTax(
        congestionTaxDto.getVehicle(),
        congestionTaxDto.getDates().stream()
            .map(
                date ->
                    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .collect(Collectors.toList()));
  }

  public void refreshRules() {
    congestionTaxUseCase.refreshRules();
  }
}
