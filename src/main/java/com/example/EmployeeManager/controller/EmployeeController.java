package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.EmployeeDTO;
import com.example.EmployeeManager.representation.EmployeeRepresentation;
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
    private final EmployeeRepresentation employeeRepresentation;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns employee by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeRepresentation.getEmployeeById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns list for all Employees", method = "GET")
    @GetMapping()
    public Page<EmployeeDTO> getAllEmployee(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllEmployee(pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create new employee", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PostMapping
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO newEmployee) {
        return employeeRepresentation.addEmployee(newEmployee);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Update employee by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PutMapping("/employees/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        return employeeRepresentation.updateEmployee(id, employee);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Dismiss employee by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PutMapping("/{id}")
    public EmployeeDTO dismissEmployee(@PathVariable Long id) {
        return employeeRepresentation.dismissEmployee(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Get all working employees", method = "GET")
    @GetMapping("/working")
    public Page<EmployeeDTO> getAllWorkingEmployees(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllWorkingEmployees(pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Get all dismissed employees", method = "GET")
    @GetMapping("/dismissed")
    public Page<EmployeeDTO> getAllDismissedEmployees(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllDismissedEmployees(pageable);
    }
}
