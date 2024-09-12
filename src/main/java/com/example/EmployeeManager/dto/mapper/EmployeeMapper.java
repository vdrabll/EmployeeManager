package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.EmployeeDTO;
import com.example.EmployeeManager.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {
    public Employee fromDto(EmployeeDTO dto) {
        Employee employee = Employee.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
        return employee;
    }

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO(employee.getFullName(), employee.getEmail(), employee.getRole());
        return dto;
    }
}

