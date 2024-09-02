package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/positions")
public class PositionController {
    private final PositionService positionService;

    @Operation(description = "Returns position by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @GetMapping("/position/{id}")
    public Position getPositionById(@PathVariable("id") Long id) {
        return positionService.getPositionById(id);
    }

    @Operation(description = "Create new leave ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of position", required = true)
    })
    @PostMapping
    public Position createPositionHistory(@RequestBody Position position) {
        return positionService.createPositionHistory(position);
    }

    @Operation(description = "Delete position by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of position", required = true)
    })
    @DeleteMapping("/delete/position/{id}")
    public void deletePositionById(@PathVariable Long id) {
        positionService.deletePositionById(id);
    }

    @Operation(description = "Update leave by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @PutMapping("/update/position/{id}")
    public Position updatePosition(@PathVariable Long id, @RequestBody Position position) {
        return positionService.updatePosition(id, position);
    }
}
