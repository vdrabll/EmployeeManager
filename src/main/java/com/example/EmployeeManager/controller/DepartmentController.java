package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.DepartmentDTO;
import com.example.EmployeeManager.dto.mapper.DepartmentMapping;
import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.service.DepartmentServiceImpl;
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

    private final DepartmentServiceImpl departmentService;
    private final DepartmentMapping mapper;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns list for all departments", method = "GET")
    @GetMapping()
    public Page<Department> getAllDepartments(@ParameterObject Pageable pageable) {
         return departmentService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns department by giving identifier", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create new department", method = "POST")
    @PostMapping
    public Department createDepartment(@RequestBody DepartmentDTO department) {
        return departmentService.save(department);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Update department by giving identifier", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentDTO department) {
        return departmentService.updateDepartmentById(id, department);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete department by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.delete(departmentService.getDepartmentById(id));
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Return all employees from department by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @GetMapping("/employees/{id}")
    public Page<Employee> getAllEmployeesFromDepartment(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return departmentService.getAllEmployeesFromDepartment(id, pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Add employee to given ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @PostMapping("{id}/employees/{employeeId}")
    public void addEmployeeToDepartment(@PathVariable Long id, @PathVariable Long employeeId) {
        departmentService.addEmployeeToDepartment(id, employeeId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete employee from department bu given id ", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @DeleteMapping("/{id}/employees/{employeeId}")
    public void removeEmployeeFromDepartment(@PathVariable Long id, @PathVariable Long employeeId) {
        departmentService.removeEmployeeFromDepartment(id,employeeId);
    }
}