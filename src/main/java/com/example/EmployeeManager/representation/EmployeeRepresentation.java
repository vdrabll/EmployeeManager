package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.createDTO.EmployeeCreateDTO;
import com.example.EmployeeManager.dto.returnDTO.EmployeeReturnDTO;
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

    public Page<EmployeeReturnDTO> getAllEmployee(Pageable pageable) {
        return employeeService.getAllEmployee(pageable).map(this::toDTO);
    }

    public EmployeeReturnDTO getEmployeeById(Long id) {
        return toDTO(employeeService.getEmployeeById(id));
    }

    public EmployeeReturnDTO addEmployee(EmployeeCreateDTO newEmployee) {
        return toDTO(employeeService.addEmployee(fromDto(newEmployee)));
    }

    public EmployeeReturnDTO addChief(EmployeeCreateDTO newEmployee) {
        return toDTO(employeeService.addChief(fromDto(newEmployee)));
    }

    public EmployeeReturnDTO updateEmployee(Long id, EmployeeCreateDTO employee) {
        Employee data = fromDto(employee);
        data.setId(id);
        return toDTO(employeeService.updateEmployee(id, data));
    }

    public EmployeeReturnDTO dismissEmployee(Long id) {
        return toDTO(employeeService.dismissEmployee(id));
    }

    public Page<EmployeeReturnDTO> getAllWorkingEmployees(Pageable pageable) {
        return employeeService.getAllWorkingEmployees(pageable).map(this::toDTO);
    }

    public Page<EmployeeReturnDTO> getAllDismissedEmployees(Pageable pageable) {
        return employeeService.getAllDismissedEmployees(pageable).map(this::toDTO);
    }

    public Employee fromDto(EmployeeCreateDTO dto) {
        Employee employee = Employee.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .build();
        return employee;
    }

    public EmployeeReturnDTO toDTO(Employee employee) {
       return new EmployeeReturnDTO( employee.getId(), employee.getFullName(), employee.getEmail(), employee.getRole().name());
    }

}

