package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotEmpty;

public record ErrorDTO(
        @NotEmpty String error,
        @NotEmpty String description) {

}
