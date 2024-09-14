package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.PositionCreateDTO;
import com.example.EmployeeManager.dto.PositionReturnDTO;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.representation.PositionRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionRepresentation positionRepresentation;

    @GetMapping("/{id}")
    @Operation(description = "Return position by giving id", method = "GET")
    public PositionReturnDTO getPositionById(@PathVariable("id") Long id) {
        return positionRepresentation.getPositionById(id);
    }

    @Operation(description = "Create new position", method = "POST")
    @PostMapping
    public PositionReturnDTO createPosition(@RequestBody Position position) {
        return positionRepresentation.createPosition(position);
    }

    @Operation(description = "Delete position by giving id", method = "DELETE")
    @DeleteMapping("/{id}")
    public void deletePositionById(@PathVariable Long id) {
        positionRepresentation.deletePositionById(id);
    }

    @Operation(description = "Update position by giving id", method = "PUT")
    @PatchMapping("/{id}")
    public PositionReturnDTO updatePosition(@PathVariable Long id, @RequestBody PositionCreateDTO position) {
        return positionRepresentation.updatePosition(id, position);
    }
}