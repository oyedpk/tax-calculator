package com.tax.calculator.TaxCalculator.infrastructure.repository;

import com.tax.calculator.TaxCalculator.infrastructure.repository.entity.FeeIntervals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollFeeIntervalRepository extends JpaRepository<FeeIntervals, Integer> {}
