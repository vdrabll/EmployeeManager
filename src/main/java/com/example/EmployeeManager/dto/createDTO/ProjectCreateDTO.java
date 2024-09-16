package com.example.EmployeeManager.dto.createDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProjectCreateDTO(
        @NotEmpty @Size(max = 100) String name,
        @NotEmpty @Size(max = 200)String description) {
}
