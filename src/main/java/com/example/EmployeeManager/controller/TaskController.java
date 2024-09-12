package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.TaskDTO;
import com.example.EmployeeManager.representation.TaskRepresentation;
import com.example.EmployeeManager.service.TaskServiceImpl;
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
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepresentation taskRepresentation;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns task by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskRepresentation.getTaskById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Create task", method = "POST")
    @PostMapping public TaskDTO saveTask(@RequestBody TaskDTO task) {
        return taskRepresentation.saveTask(task);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Update task by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO task) {
        return taskRepresentation.updateTask(id, task);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Delete task by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepresentation.deleteTask(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns all tasks of employee by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employees/{id}")
    public Page<TaskDTO> getAllTasksOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return taskRepresentation.getAllTasksOfEmployee(id, pageable);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Assign task to employee by giving id", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true),
            @Parameter(name = "task", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @PostMapping("/employees/{id}")
    public TaskDTO assignTaskToEmployee(@PathVariable Long id, @RequestBody TaskDTO task, @ParameterObject Pageable pageable) {
        return taskRepresentation.assignTaskToEmployee(id, task, pageable);
    }
}