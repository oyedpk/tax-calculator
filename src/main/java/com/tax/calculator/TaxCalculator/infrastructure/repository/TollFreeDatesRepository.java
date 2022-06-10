package com.tax.calculator.TaxCalculator.infrastructure.repository;

import com.tax.calculator.TaxCalculator.infrastructure.repository.entity.TollFreeDates;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollFreeDatesRepository extends JpaRepository<TollFreeDates, LocalDate> {}
