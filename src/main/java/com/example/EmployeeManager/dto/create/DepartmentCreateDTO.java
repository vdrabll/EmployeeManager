package com.example.EmployeeManager.dto.create;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record DepartmentCreateDTO(
        @NotEmpty  String name,
        @NotEmpty String location) {
}
