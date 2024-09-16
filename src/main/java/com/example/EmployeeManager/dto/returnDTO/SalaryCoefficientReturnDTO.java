package com.example.EmployeeManager.dto.returnDTO;

import java.math.BigDecimal;

public record SalaryCoefficientReturnDTO(
        Long id,
        BigDecimal advancePercentage,
        BigDecimal bonusPercentage) {
}
