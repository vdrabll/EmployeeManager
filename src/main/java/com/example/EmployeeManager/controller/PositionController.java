package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.PositionDTO;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.representation.PositionRepresentation;
import com.example.EmployeeManager.service.PositionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    final PositionRepresentation positionRepresentation;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns position by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @GetMapping("/{id}")
    public PositionDTO getPositionById(@PathVariable("id") Long id) {
        return positionRepresentation.getPositionById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create new position ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of position", required = true)
    })
    @PostMapping
    public PositionDTO createPosition(@RequestBody Position position) {
        return positionRepresentation.createPosition(position);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete position by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of position", required = true)
    })
    @DeleteMapping("/{id}")
    public void deletePositionById(@PathVariable Long id) {
        positionRepresentation.deletePositionById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Update position by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @PutMapping("/{id}")
    public PositionDTO updatePosition(@PathVariable Long id, @RequestBody PositionDTO position) {
        return positionRepresentation.updatePosition(id, position);
    }
}
