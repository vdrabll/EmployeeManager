package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.EmployeeDTO;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.representation.EmployeeRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepresentation employeeRepresentation;

    @Operation(description = "Get employee by given id", method = "GET")
    @GetMapping("/get/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeRepresentation.getEmployeeById(id);
    }

    @Operation(description = "Get employee by given id", method = "GET")
    @GetMapping("/all")
    public Page<EmployeeDTO> getAllEmployee(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllEmployee(pageable);
    }

    @Operation(description = "Create new employee", method = "POST")
    @PostMapping("/add")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO newEmployee) {
        newEmployee.setRole(new Role(AuthRole.EMPLOYEE));
        return employeeRepresentation.addEmployee(newEmployee);
    }

    @Operation(description = "Update employee by given id", method = "PUT")
    @PutMapping("/employees/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        return employeeRepresentation.updateEmployee(id, employee);
    }

    @Operation(description = "Dismiss employee by given id", method = "PUT")
    @PutMapping("/dismiss/{id}")
    public EmployeeDTO dismissEmployee(@PathVariable Long id) {
        return employeeRepresentation.dismissEmployee(id);
    }

    @Operation(description = "Return all working employees", method = "GET")
    @GetMapping("/working")
    public Page<EmployeeDTO> getAllWorkingEmployees(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllWorkingEmployees(pageable);
    }

    @Operation(description = "Return all dismissed working employees", method = "GET")
    @GetMapping("/dismissed")
    public Page<EmployeeDTO> getAllDismissedEmployees(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllDismissedEmployees(pageable);
    }
}
