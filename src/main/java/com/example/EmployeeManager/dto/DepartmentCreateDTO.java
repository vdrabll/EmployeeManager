package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record DepartmentCreateDTO(

        @NotEmpty  String name,
        @NotEmpty String location) {
}
