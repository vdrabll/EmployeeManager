package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.TaskCreateDTO;
import com.example.EmployeeManager.dto.TaskReturnDTO;
import com.example.EmployeeManager.representation.TaskRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepresentation taskRepresentation;

    @Operation(description = "Get task by giving id", method = "GET")
    @GetMapping("/{id}")
    public TaskReturnDTO getTaskById(@PathVariable Long id) {
        return taskRepresentation.getTaskById(id);
    }

    @Operation(description = "Save new task", method = "POST")
    @PostMapping
    public TaskReturnDTO saveTask(@RequestBody TaskCreateDTO task) {
        return taskRepresentation.saveTask(task);
    }

    @Operation(description = "Update task by giving id", method = "PUT")
    @PutMapping("/{id}")
    public TaskReturnDTO updateTask(@PathVariable Long id, @RequestBody TaskCreateDTO task) {
        return taskRepresentation.updateTask(id, task);
    }

    @Operation(description = "Delete  task by giving id", method = "DELETE")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepresentation.deleteTask(id);
    }

    @Operation(description = "Get all task if employee by giving id", method = "GET")
    @GetMapping("/employees/{id}")
    public Page<TaskReturnDTO> getAllTasksOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return taskRepresentation.getAllTasksOfEmployee(id, pageable);
    }

    @Operation(description = "assign task to employee by giving id", method = "POST")
    @PostMapping("/employees/{id}")
    public TaskReturnDTO assignTaskToEmployee(@PathVariable Long id,
                                              @RequestBody TaskCreateDTO task,
                                              @ParameterObject Pageable pageable) {
        return taskRepresentation.assignTaskToEmployee(id, task, pageable);
    }
}