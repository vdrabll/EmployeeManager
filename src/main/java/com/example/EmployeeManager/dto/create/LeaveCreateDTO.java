package com.example.EmployeeManager.dto.create;

import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record LeaveCreateDTO(
        @NotEmpty LocalDate startDate,
        @NotEmpty LocalDate endDate,
        @NotEmpty LeaveType type,
        @NotEmpty LeaveStatus status) {
}
