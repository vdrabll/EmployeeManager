package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskReturnDTO(
        Long id,
        String name,
        String description,
        TaskPriority priority,
        LocalDate deadline,
        TaskStatus status,
        String type) {
}
