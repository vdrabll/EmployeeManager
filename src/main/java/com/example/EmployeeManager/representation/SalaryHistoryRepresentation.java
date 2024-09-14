package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.SalaryHistoryReturnDTO;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalaryHistoryRepresentation {
    private final SalaryHistoryService salaryHistoryService;

    public SalaryHistoryReturnDTO getSalaryHistoryById(Long id) {
        return toDto(salaryHistoryService.getSalaryHistoryById(id));
    }

    SalaryHistoryReturnDTO toDto(SalaryHistory salaryHistory) {
        return new SalaryHistoryReturnDTO(salaryHistory.getId(), salaryHistory.getSalaryDate(), salaryHistory.getAmount(), salaryHistory.getType());
    }

    public Page<SalaryHistoryReturnDTO> getSalaryHistoryOfEmployee(Long id, Pageable pageable) {
        return salaryHistoryService.getSalaryHistoryOfEmployee(id, pageable).map(this::toDto);
    }

}