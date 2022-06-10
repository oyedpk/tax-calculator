package com.tax.calculator.TaxCalculator.infrastructure.controller;

import static com.tax.calculator.TaxCalculator.infrastructure.config.Constants.*;

import com.tax.calculator.TaxCalculator.adaptor.inbound.http.CongestionTaxInAdaptor;
import com.tax.calculator.TaxCalculator.adaptor.outbound.repository.TollRulesOutAdaptor;
import com.tax.calculator.TaxCalculator.domain.TollFeeForTime;
import com.tax.calculator.TaxCalculator.infrastructure.controller.dto.CongestionTaxDto;
import java.time.LocalDate;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(V1)
@RequiredArgsConstructor
public class CongestionTaxController {

  private final CongestionTaxInAdaptor congestionTaxInAdaptor;

  private final TollRulesOutAdaptor tollRulesOutAdaptor;

  @PostMapping(value = CONGESTION_TAX)
  public ResponseEntity<Float> calculateCongestionTax(
      @RequestBody CongestionTaxDto congestionTaxDto) {
    return new ResponseEntity(
        congestionTaxInAdaptor.calculateCongestionTax(congestionTaxDto), HttpStatus.OK);
  }

  @PostMapping(value = REFRESH_RULES)
  public ResponseEntity<String> refreshRules() {
    congestionTaxInAdaptor.refreshRules();
    return new ResponseEntity("Refreshed Rules", HttpStatus.OK);
  }

  @GetMapping(value = PING)
  public ResponseEntity<String> ping() {
    return new ResponseEntity("pong", HttpStatus.OK);
  }

  @GetMapping(value = TOLL_FREE_VEHICLE)
  public ResponseEntity<Set<String>> tollFreeVehicles() {
    return new ResponseEntity(tollRulesOutAdaptor.getTollFreeVehicles(), HttpStatus.OK);
  }

  @GetMapping(value = TOLL_FREE_DATE)
  public ResponseEntity<Set<LocalDate>> tollFreeDates() {
    return new ResponseEntity(tollRulesOutAdaptor.getTollFreeDates(), HttpStatus.OK);
  }

  @GetMapping(value = FEE_INTERVAL)
  public ResponseEntity<Set<TollFeeForTime>> feeInterval() {
    return new ResponseEntity(tollRulesOutAdaptor.getTollFees(), HttpStatus.OK);
  }
}
