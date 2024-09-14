package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record PositionCreateDTO(
        @NotEmpty Short grade,
        @NotEmpty BigDecimal salary,
        @NotEmpty String name) {
}