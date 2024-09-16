package com.example.EmployeeManager.dto.createDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record DepartmentCreateDTO(
        @NotEmpty  String name,
        @NotEmpty String location) {
}
