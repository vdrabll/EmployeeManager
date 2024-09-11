package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.service.ProjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectServiceImpl projectService;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns project by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create project by giving id", method = "POST")
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete project by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Update project by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Add employee to project", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PostMapping("/{id}/employees/{employeeId}")
    public Project addEmployeeToProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectService.addEmployeeToProject(id, employeeId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete employee from project", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @DeleteMapping("/{id}/employee/{employeeId}")
    public Project removeEmployeeFromProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectService.removeEmployeeFromProject(id, employeeId);
    }
}