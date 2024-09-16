package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.enums.SalaryType;


import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryHistoryReturnDTO(
        Long id,
        LocalDate salaryDate,
        BigDecimal amount,
        SalaryType type) {

}
