package com.example.EmployeeManager.dto.createDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmployeeCreateDTO(
        @NotEmpty @Email String fullName,
        @NotEmpty String email) {
}
