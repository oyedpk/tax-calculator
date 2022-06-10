package com.tax.calculator.TaxCalculator.infrastructure.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "fee_interval")
@Data
@RequiredArgsConstructor
public class FeeIntervals {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, name = "time_interval")
  private String timeInterval;

  @Column(nullable = false)
  private Float fee;
}
