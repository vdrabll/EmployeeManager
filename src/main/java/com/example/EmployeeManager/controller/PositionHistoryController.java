package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.service.PositionHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/position-histories")
public class PositionHistoryController {

    private final PositionHistoryServiceImpl positionHistoryService;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns position history record by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of record", required = true)
    })
    @GetMapping("/{id}")
    public PositionHistory getPositionHistoryRecordById(@PathVariable Long id) {
        return positionHistoryService.getPositionById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create position history record", method = "POST")
    @PostMapping
    public PositionHistory createPositionHistory(@RequestBody PositionHistory positionHistory) {
        return positionHistoryService.createPositionHistory(positionHistory);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete position history record by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @DeleteMapping("/{id}")
    public void deletePositionHistoryById(@PathVariable("id") Long id) {
        positionHistoryService.deletePositionHistoryById(id);
    }
}