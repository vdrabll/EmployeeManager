package com.example.EmployeeManager.dto.createDTO;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record PositionHistoryCreateDTO(
        @NotEmpty LocalDate startDate,
        @NotEmpty LocalDate endDate) {
}
