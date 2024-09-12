package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.ProjectDTO;
import com.example.EmployeeManager.representation.ProjectRepresentation;
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
    private final ProjectRepresentation projectRepresentation;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns project by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        return projectRepresentation.getProjectById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Create project by giving id", method = "POST")
    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return projectRepresentation.createProject(project);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete project by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable Long id) {
        projectRepresentation.deleteProjectById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Update project by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
        return projectRepresentation.updateProject(id, project);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Add employee to project", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PostMapping("/{id}/employees/{employeeId}")
    public ProjectDTO addEmployeeToProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectRepresentation.addEmployeeToProject(id, employeeId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete employee from project", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @DeleteMapping("/{id}/employee/{employeeId}")
    public ProjectDTO removeEmployeeFromProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectRepresentation.removeEmployeeFromProject(id, employeeId);
    }
}