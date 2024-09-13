package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.PositionHistoryDTO;
import com.example.EmployeeManager.representation.PositionHistoryRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/position-histories")
public class PositionHistoryController {
    private final PositionHistoryRepresentation positionHistoryRepresentation;

    @Operation(description = "Return position history by giving id", method = "GET")
    @GetMapping("/get/{id}")
    public PositionHistoryDTO getPositionHistoryById(@PathVariable Long id) {
        return positionHistoryRepresentation.getPositionHistoryById(id);
    }

    @Operation(description = "create new position by giving id", method = "POST")
    @PostMapping("/create")
    public PositionHistoryDTO createPositionHistory(@RequestBody PositionHistoryDTO positionHistory) {
        return positionHistoryRepresentation.createPositionHistory(positionHistory);
    }

    @Operation(description = "Delete position by giving id", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public void deletePositionHistoryById(@PathVariable("id") Long id) {
        positionHistoryRepresentation.deletePositionHistoryById(id);
    }
}