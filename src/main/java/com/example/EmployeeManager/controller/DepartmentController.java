package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.service.DepartmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @Operation(description = "Returns list for all departments", method = "GET")
    @PreAuthorize("hasRole(ROLE_CHEIF)")
    @GetMapping()
    public Page<Department> getAllDepartments(@ParameterObject Pageable pageable) {
         return departmentService.getAll(pageable);
    }

    @Operation(description = "Returns department by giving identifier", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") Long id) {
        return departmentService.getDepartmentById(id);
    }

    @Operation(description = "Create new department", method = "POST")
    @ApiResponse
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.save(department);
    }

    @Operation(description = "Update department by giving identifier", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable("id") Long id, @RequestBody Department department) {
        return departmentService.updateDepartmentById(id, department);
    }

    @Operation(description = "Delete department by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.delete(departmentService.getDepartmentById(id));
    }

    @Operation(description = "Return all employees from department by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @GetMapping("/employees/{id}")
    public Page<Employee> getAllEmployeesFromDepartment(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return departmentService.getAllEmployeesFromDepartment(id, pageable);
    }

    @Operation(description = "Add employee to given ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @PostMapping("/employees/{id}")
    public void addEmployeeToDepartment(@PathVariable Long id, @RequestBody Employee newEmployee) {
        departmentService.addEmployeeToDepartment(id, newEmployee);
    }

    @Operation(description = "Delete employee from department bu given id ", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @ApiResponse
    @DeleteMapping("/{id}/employees/{employeeId}")
    public void removeEmployeeFromDepartment(@PathVariable Long departmentId, @PathVariable Long employeeId) {
        departmentService.removeEmployeeFromDepartment(departmentId,employeeId);
    }
}