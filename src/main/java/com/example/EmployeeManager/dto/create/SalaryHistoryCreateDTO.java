package com.example.EmployeeManager.dto.create;

import com.example.EmployeeManager.enums.SalaryType;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;



public record SalaryHistoryCreateDTO(
        @NotEmpty LocalDate salaryDate,
        @NotEmpty BigDecimal amount,
        @NotEmpty SalaryType type) {
}
