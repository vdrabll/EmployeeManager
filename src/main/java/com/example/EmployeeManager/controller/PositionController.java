package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.PositionDTO;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.representation.PositionRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionRepresentation positionRepresentation;

    @GetMapping("/get/{id}")
    @Operation(description = "Return position by giving id", method = "GET")
    public PositionDTO getPositionById(@PathVariable("id") Long id) {
        return positionRepresentation.getPositionById(id);
    }

    @Operation(description = "Create new position", method = "POST")
    @PostMapping("/create")
    public PositionDTO createPosition(@RequestBody Position position) {
        return positionRepresentation.createPosition(position);
    }

    @Operation(description = "Delete position by giving id", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public void deletePositionById(@PathVariable Long id) {
        positionRepresentation.deletePositionById(id);
    }

    @Operation(description = "Update position by giving id", method = "PUT")
    @PutMapping("/update/{id}")
    public PositionDTO updatePosition(@PathVariable Long id, @RequestBody PositionDTO position) {
        return positionRepresentation.updatePosition(id, position);
    }
}