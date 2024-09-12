package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.DepartmentDTO;
import com.example.EmployeeManager.dto.EmployeeDTO;
import com.example.EmployeeManager.representation.DepartmentRepresentation;
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
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepresentation departmentRepresentation;


    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns list for all departments", method = "GET")
    @GetMapping()
    public Page<DepartmentDTO> getAllDepartments(@ParameterObject Pageable pageable) {
         return departmentRepresentation.getAllDepartments(pageable) ;
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns department by giving identifier", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @GetMapping("/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable("id") Long id) {
        return departmentRepresentation.getDepartmentById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create new department", method = "POST")
    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO department) {
        return departmentRepresentation.createDepartment(department);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Update department by giving identifier", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @PutMapping("/{id}")
    public DepartmentDTO updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentDTO department) {
        return departmentRepresentation.updateDepartment(id, department);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete department by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentRepresentation.deleteDepartment(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Return all employees from department by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @GetMapping("/employees/{id}")
    public Page<EmployeeDTO> getAllEmployeesFromDepartment(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return departmentRepresentation.getAllEmployeesFromDepartment(id, pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Add employee to given ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @PostMapping("{id}/employees/{employeeId}")
    public void addEmployeeToDepartment(@PathVariable Long id, @PathVariable Long employeeId) {
        departmentRepresentation.addEmployeeToDepartment(id, employeeId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete employee from department bu given id ", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @DeleteMapping("/{id}/employees/{employeeId}")
    public void removeEmployeeFromDepartment(@PathVariable Long id, @PathVariable Long employeeId) {
        departmentRepresentation.removeEmployeeFromDepartment(id,employeeId);
    }
}