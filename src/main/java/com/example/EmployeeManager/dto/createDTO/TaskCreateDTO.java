package com.example.EmployeeManager.dto.createDTO;

import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskCreateDTO(
        @Size(max = 100) @NotEmpty String name,
        @Size(max = 500) @NotEmpty String description,
        @NotEmpty TaskPriority priority,
        @NotEmpty LocalDate deadline,
        @NotEmpty TaskStatus status,
        @NotEmpty String type) {
}
