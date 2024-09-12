package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.SalaryHistoryDTO;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class SalaryHistoryRepresentation {
    private final SalaryHistoryService salaryHistoryService;

    public SalaryHistoryDTO getSalaryHistoryById(Long id) {
        return toDto(salaryHistoryService.getSalaryHistoryById(id));
    }

    SalaryHistoryDTO toDto(SalaryHistory salaryHistory) {
        SalaryHistoryDTO dto = new SalaryHistoryDTO(salaryHistory.getSalaryDate(), salaryHistory.getAmount(), salaryHistory.getType());
        return dto;
    }

    public Page<SalaryHistoryDTO> getSalaryHistoryOfEmployee(Long id, Pageable pageable) {
        return salaryHistoryService.getSalaryHistoryOfEmployee(id, pageable).map(this::toDto);
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
