package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Employee employee;
    private LeaveType type;
    private LeaveStatus status;
}
