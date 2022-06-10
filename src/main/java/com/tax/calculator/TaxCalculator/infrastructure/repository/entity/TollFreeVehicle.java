package com.tax.calculator.TaxCalculator.infrastructure.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "toll_free_vehicle")
@Data
@RequiredArgsConstructor
public class TollFreeVehicle {

  @Id
  @Column(nullable = false)
  private String vehicle;
}
