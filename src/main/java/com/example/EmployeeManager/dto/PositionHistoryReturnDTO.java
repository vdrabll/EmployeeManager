package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record PositionHistoryReturnDTO(
        Long id,
        LocalDate startDate,
        LocalDate endDate) {
}
