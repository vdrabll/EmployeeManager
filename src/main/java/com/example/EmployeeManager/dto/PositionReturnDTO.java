package com.example.EmployeeManager.dto;

import java.math.BigDecimal;

public record PositionReturnDTO(
        Long id,
        Short grade,
        BigDecimal salary,
        String name) {
}
