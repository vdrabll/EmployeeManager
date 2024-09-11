package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.service.SalaryHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salary-histories")
public class SalaryHistoryController {

    private final SalaryHistoryServiceImpl salaryHistoryService;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns salary history by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of salary history", required = true)
    })
    @GetMapping("/{id}")
    public SalaryHistory getSalaryHistoryById(@PathVariable Long id) {
        return salaryHistoryService.getSalaryHistoryById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns salary history by giving employee id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employees/{id}")
    public Page<SalaryHistory> getSalaryHistoryOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return salaryHistoryService.getSalaryHistoryOfEmployee(id, pageable);
    }
}