package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmployeeReturnDTO(
        Long id,
        String fullName,
        String email,
        String Role) {
}
