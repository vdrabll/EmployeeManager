package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentReturnDTO(
        Long id,
        String name,
        String location) {
}