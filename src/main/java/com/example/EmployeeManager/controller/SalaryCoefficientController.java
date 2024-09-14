package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.SalaryCoefficientCreateDTO;
import com.example.EmployeeManager.dto.SalaryCoefficientReturnDTO;
import com.example.EmployeeManager.representation.SalaryCoefficientRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @Operation(description = "Создание нового коэффициента")
    @PostMapping
    public SalaryCoefficientReturnDTO createNewCoefficient(@PathVariable int year, @RequestBody SalaryCoefficientCreateDTO coefficient) {
        return representation.createNewCoefficien(coefficient);
    }

    @Operation(description = "Получение коэффициента для конкретного года")
    @GetMapping("/coefficient/{year}")
    public SalaryCoefficientReturnDTO getCoefficientOfYear(@PathVariable int year) {
        return representation.getCoefficientOfYear(LocalDate.of(year, 1, 1));
    }


}
