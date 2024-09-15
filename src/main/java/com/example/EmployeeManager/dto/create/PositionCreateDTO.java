package com.example.EmployeeManager.dto.create;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record PositionCreateDTO(
        @NotEmpty Short grade,
        @NotEmpty BigDecimal salary,
        @NotEmpty String name) {
}