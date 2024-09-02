package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.service.SalaryHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salary-histories")
public class SalaryHistoryController {

    private final SalaryHistoryService salaryHistoryService;

    @Operation(description = "Returns salary history by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of salary history", required = true)
    })
    @GetMapping("/salary-history/{id}")
    public SalaryHistory getSalaryHistoryById(@PathVariable Long id) {
        return salaryHistoryService.getSalaryHistoryById(id);
    }

    @Operation(description = "Create salary history by giving id", method = "POST")
    @PostMapping
    public SalaryHistory createSalaryHistory(@RequestBody SalaryHistory salary) {
        return salaryHistoryService.createSalaryHistory(salary);
    }

    @Operation(description = "Delete salary history by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of salary history", required = true)
    })
    @DeleteMapping("/delete/{id}")
    public void deleteSalaryHistoryById(@PathVariable Long id) {
        salaryHistoryService.deleteSalaryHistoryById(id);
    }

    @Operation(description = "Returns salary history by giving employee id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/salary-history/employee/{id}")
    public List<SalaryHistory> getSalaryHistoryOfEmployee(@PathVariable Long employeeId) {
        return salaryHistoryService.getSalaryHistoryOfEmployee(employeeId);
    }
}