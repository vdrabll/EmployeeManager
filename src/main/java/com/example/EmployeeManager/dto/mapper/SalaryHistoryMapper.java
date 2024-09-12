package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.SalaryHistoryDTO;
import com.example.EmployeeManager.entity.SalaryHistory;
import org.springframework.stereotype.Service;

@Service
public class SalaryHistoryMapper {
    SalaryHistoryDTO toDto(SalaryHistory salaryHistory) {
        SalaryHistoryDTO dto = new SalaryHistoryDTO(salaryHistory.getSalaryDate(), salaryHistory.getAmount(), salaryHistory.getType());
        return dto;
    }

    SalaryHistory fromDto(SalaryHistoryDTO dto) {
        SalaryHistory salaryHistory = SalaryHistory.builder()
                .salaryDate(dto.getSalaryDate())
                .amount(dto.getAmount())
                .type(dto.getType())
                .build();
        return salaryHistory;
    }
}
