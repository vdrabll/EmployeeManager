package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.EmployeeDTO;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeRepresentation {
    private final EmployeeService employeeService;

    public Page<EmployeeDTO> getAllEmployee(Pageable pageable) {
        return employeeService.getAllEmployee(pageable).map(this::toDTO);
    }

    public EmployeeDTO getEmployeeById( Long id) {
        return toDTO(employeeService.getEmployeeById(id));
    }

    public EmployeeDTO addEmployee(EmployeeDTO newEmployee) {
        return toDTO(employeeService.addEmployee(fromDto(newEmployee)));
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee) {
        Employee data = fromDto(employee);
        data.setId(id);
        return toDTO(employeeService.updateEmployee(id, data));
    }

    public EmployeeDTO dismissEmployee(Long id) {
        return toDTO(employeeService.dismissEmployee(id));
    }

    public Page<EmployeeDTO> getAllWorkingEmployees(Pageable pageable) {
        return employeeService.getAllWorkingEmployees(pageable).map(this::toDTO);
    }

    public Page<EmployeeDTO> getAllDismissedEmployees(Pageable pageable) {
        return employeeService.getAllDismissedEmployees(pageable).map(this::toDTO);
    }

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

