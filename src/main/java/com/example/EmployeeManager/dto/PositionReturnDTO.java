package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record PositionReturnDTO(
        Long id,
        Short grade,
        BigDecimal salary,
        String name) {
}
