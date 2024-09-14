package com.example.EmployeeManager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryCoefficientCreateDTO(
        LocalDate date,
        BigDecimal advancePercentage,
        BigDecimal bonusPercentage) {
}
