package com.example.EmployeeManager.dto.returnDTO;

import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;

import java.time.LocalDate;

public record LeaveReturnDTO(
        Long id,
        LocalDate startDate,
        LocalDate endDate,
        LeaveType type,
        LeaveStatus status) {
}