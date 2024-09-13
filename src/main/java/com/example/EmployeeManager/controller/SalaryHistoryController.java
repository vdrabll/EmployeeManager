package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.SalaryHistoryDTO;
import com.example.EmployeeManager.representation.SalaryHistoryRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salary-histories")
public class SalaryHistoryController {

    private final SalaryHistoryRepresentation salaryHistoryRepresentation;

    @Operation(description = "Get salary history by giving id to project by giving id", method = "GET")
    @GetMapping("/get/{id}")
    public SalaryHistoryDTO getSalaryHistoryById(@PathVariable Long id) {
        return salaryHistoryRepresentation.getSalaryHistoryById(id);
    }

    @Operation(description = "Get salary history by giving id", method = "GET")
    @GetMapping("/employees/{id}")
    public Page<SalaryHistoryDTO> getSalaryHistoryOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return salaryHistoryRepresentation.getSalaryHistoryOfEmployee(id, pageable);
    }
}