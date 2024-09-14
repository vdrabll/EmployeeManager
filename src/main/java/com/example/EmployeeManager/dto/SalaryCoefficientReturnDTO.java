package com.example.EmployeeManager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryCoefficientReturnDTO(
        Long id,
        LocalDate date,
        BigDecimal advancePercentage,
        BigDecimal bonusPercentage) {
}
