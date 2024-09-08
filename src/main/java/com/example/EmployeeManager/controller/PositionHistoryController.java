package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.service.PositionHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/position-historie")
public class PositionHistoryController {

    private final PositionHistoryServiceImpl positionHistoryService;

    @Operation(description = "Returns project by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @GetMapping("/{id}")
    public PositionHistory getPositionHistoryRecordById(@PathVariable Long id) {
        return positionHistoryService.getPositionById(id);
    }

    @Operation(description = "Create position history record", method = "POST")
    @PostMapping
    public PositionHistory createPositionHistory(@RequestBody PositionHistory positionHistory) {
        return positionHistoryService.createPositionHistory(positionHistory);
    }

    @Operation(description = "Delete position history record by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @DeleteMapping("/{id}")
    public void deletePositionHistoryById(@PathVariable("id") Long id) {
        positionHistoryService.deletePositionHistoryById(id);
    }
}