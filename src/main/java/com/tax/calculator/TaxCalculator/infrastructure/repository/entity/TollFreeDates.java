package com.tax.calculator.TaxCalculator.infrastructure.repository.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "toll_free_date")
@Data
@RequiredArgsConstructor
public class TollFreeDates {

  @Id @Column private LocalDate date;
}
