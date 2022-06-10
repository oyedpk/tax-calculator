package com.tax.calculator.TaxCalculator.component;

import com.tax.calculator.TaxCalculator.infrastructure.controller.CongestionTaxController;
import com.tax.calculator.TaxCalculator.infrastructure.controller.dto.CongestionTaxDto;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaxCalculatorComponentTests {

  @Autowired CongestionTaxController congestionTaxController;

  @Test
  public void testCalculateCongestionTax_within60min() {
    CongestionTaxDto congestionTaxDto =
        CongestionTaxDto.builder()
            .dates(List.of("2013-01-14 06:00:00", "2013-01-14 06:30:00"))
            .vehicle("CAR")
            .build();
    ResponseEntity<Float> response =
        congestionTaxController.calculateCongestionTax(congestionTaxDto);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(Float.valueOf(13), response.getBody());
  }

  @Test
  public void testCalculateCongestionTax_greaterThan60minV1() {
    CongestionTaxDto congestionTaxDto =
        CongestionTaxDto.builder()
            .dates(List.of("2013-01-14 06:00:00", "2013-01-14 07:30:00"))
            .vehicle("CAR")
            .build();
    ResponseEntity<Float> response =
        congestionTaxController.calculateCongestionTax(congestionTaxDto);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(Float.valueOf(26), response.getBody());
  }

  @Test
  public void testCalculateCongestionTax_greaterThan60minV2() {
    CongestionTaxDto congestionTaxDto =
        CongestionTaxDto.builder()
            .dates(List.of("2013-01-14 06:00:00", "2013-01-14 07:30:00", "2013-01-14 07:45:00"))
            .vehicle("CAR")
            .build();
    ResponseEntity<Float> response =
        congestionTaxController.calculateCongestionTax(congestionTaxDto);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(Float.valueOf(26), response.getBody());
  }

  @Test
  public void testCalculateCongestionTax_differentDates() {
    CongestionTaxDto congestionTaxDto =
        CongestionTaxDto.builder()
            .dates(List.of("2013-01-14 06:00:00", "2013-01-15 07:30:00", "2013-01-16 07:45:00"))
            .vehicle("CAR")
            .build();
    ResponseEntity<Float> response =
        congestionTaxController.calculateCongestionTax(congestionTaxDto);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(Float.valueOf(44), response.getBody());
  }

  @Test
  public void testCalculateCongestionTax_tollFreeVehicle() {
    CongestionTaxDto congestionTaxDto =
        CongestionTaxDto.builder()
            .dates(List.of("2013-01-14 06:00:00", "2013-01-15 07:30:00", "2013-01-16 07:45:00"))
            .vehicle("Military")
            .build();
    ResponseEntity<Float> response =
        congestionTaxController.calculateCongestionTax(congestionTaxDto);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(Float.valueOf(0), response.getBody());
  }

  @Test
  public void testCalculateCongestionTax_tollFreeDates() {
    CongestionTaxDto congestionTaxDto =
        CongestionTaxDto.builder()
            .dates(List.of("2013-01-01 06:00:00", "2013-03-28 07:30:00", "2013-03-22 07:45:00"))
            .vehicle("Car")
            .build();
    ResponseEntity<Float> response =
        congestionTaxController.calculateCongestionTax(congestionTaxDto);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(Float.valueOf(18), response.getBody());
  }
}
