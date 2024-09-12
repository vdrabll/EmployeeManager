package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.DepartmentDTO;
import com.example.EmployeeManager.entity.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapping {
    public Department fromDTO(DepartmentDTO dto) {
        Department department = Department.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .build();
        return department;
    }

    public DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO(department.getName(), department.getLocation());
        return dto;
    }
}
