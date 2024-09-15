package com.example.EmployeeManager.dto.create;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record PositionHistoryCreateDTO(
        @NotEmpty LocalDate startDate,
        @NotEmpty LocalDate endDate) {
}
