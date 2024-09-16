package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.returnDTO.SalaryCoefficientReturnDTO;
import com.example.EmployeeManager.representation.SalaryCoefficientRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/coefficient")
public class SalaryCoefficientController {
    private final SalaryCoefficientRepresentation representation;

    @Operation( description = "Получение всех коэффициентов")
    @GetMapping("/coefficients")
    public Page<SalaryCoefficientReturnDTO> getAllCoefficients() {
        return representation.getAllCoefficients();
    }

}
