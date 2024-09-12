package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.enums.SalaryType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryHistoryDTO {
    private LocalDate salaryDate;
    private BigDecimal amount;
    private SalaryType type;
}
