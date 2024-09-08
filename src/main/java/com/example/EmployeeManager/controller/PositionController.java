package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.service.PositionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionServiceImpl positionService;

    @Operation(description = "Returns position by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @GetMapping("/{id}")
    public Position getPositionById(@PathVariable("id") Long id) {
        return positionService.getPositionById(id);
    }

    @Operation(description = "Create new leave ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of position", required = true)
    })
    @PostMapping
    public Position createPositionHistory(@RequestBody Position position) {
        return positionService.createPosition(position);
    }

    @Operation(description = "Delete position by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of position", required = true)
    })
    @DeleteMapping("/{id}")
    public void deletePositionById(@PathVariable Long id) {
        positionService.deletePositionById(id);
    }

    @Operation(description = "Update leave by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @PutMapping("/{id}")
    public Position updatePosition(@PathVariable Long id, @RequestBody Position position) {
        return positionService.updatePosition(id, position);
    }
}
