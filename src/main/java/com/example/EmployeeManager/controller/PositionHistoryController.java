package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.create.PositionHistoryCreateDTO;
import com.example.EmployeeManager.dto.PositionHistoryReturnDTO;
import com.example.EmployeeManager.representation.PositionHistoryRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/position-histories")
public class PositionHistoryController {
    private final PositionHistoryRepresentation positionHistoryRepresentation;

    @Operation(description = "Return position history by giving id", method = "GET")
    @GetMapping("/{id}")
    public PositionHistoryReturnDTO getPositionHistoryById(@PathVariable Long id) {
        return positionHistoryRepresentation.getPositionHistoryById(id);
    }

    @Operation(description = "create new position", method = "POST")
    @PostMapping
    public PositionHistoryReturnDTO createPositionHistory(@RequestBody PositionHistoryCreateDTO positionHistory) {
        return positionHistoryRepresentation.createPositionHistory(positionHistory);
    }

    @Operation(description = "Delete position by giving id", method = "DELETE")
    @DeleteMapping("/{id}")
    public void deletePositionHistoryById(@PathVariable("id") Long id) {
        positionHistoryRepresentation.deletePositionHistoryById(id);
    }
}