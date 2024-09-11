package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns employee by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeServiceImpl.getEmployeeById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns list for all Employees", method = "GET")
    @GetMapping()
    public Page<Employee> getAllEmployee(@ParameterObject Pageable pageable) {
        return employeeServiceImpl.getAllEmployee(pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create new employee", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PostMapping
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        return employeeServiceImpl.addEmployee(newEmployee);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Update employee by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeServiceImpl.updateEmployee(id, employee);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Dismiss employee by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PutMapping("/{id}")
    public Employee dismissEmployee(@PathVariable Long id) {
        return employeeServiceImpl.dismissEmployee(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Get all working employees", method = "GET")
    @GetMapping("/working")
    public Page<Employee> getAllWorkingEmployees(@ParameterObject Pageable pageable) {
        return employeeServiceImpl.getAllWorkingEmployees(pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Get all dismissed employees", method = "GET")
    @GetMapping("/dismissed")
    public Page<Employee> getAllDismissedEmployees(@ParameterObject Pageable pageable) {
        return employeeServiceImpl.getAllDismissedEmployees(pageable);
    }
}
