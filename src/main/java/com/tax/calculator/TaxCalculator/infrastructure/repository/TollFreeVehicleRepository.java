package com.tax.calculator.TaxCalculator.infrastructure.repository;

import com.tax.calculator.TaxCalculator.infrastructure.repository.entity.TollFreeVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollFreeVehicleRepository extends JpaRepository<TollFreeVehicle, String> {}
