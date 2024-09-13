package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.TaskDTO;
import com.example.EmployeeManager.representation.TaskRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepresentation taskRepresentation;

    @Operation(description = "Get task by giving id", method = "GET")
    @GetMapping("/get/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskRepresentation.getTaskById(id);
    }

    @Operation(description = "Save new task", method = "POST")
    @PostMapping ("/save")
    public TaskDTO saveTask(@RequestBody TaskDTO task) {
        return taskRepresentation.saveTask(task);
    }

    @Operation(description = "Update task by giving id", method = "PUT")
    @PutMapping("/update/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO task) {
        return taskRepresentation.updateTask(id, task);
    }

    @Operation(description = "Delete  task by giving id", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepresentation.deleteTask(id);
    }

    @Operation(description = "Get all task if employee by giving id", method = "GET")
    @GetMapping("/employees/{id}")
    public Page<TaskDTO> getAllTasksOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return taskRepresentation.getAllTasksOfEmployee(id, pageable);
    }

    @Operation(description = "assign task to employee by giving id", method = "POST")
    @PostMapping("/employees/{id}")
    public TaskDTO assignTaskToEmployee(@PathVariable Long id, @RequestBody TaskDTO task, @ParameterObject Pageable pageable) {
        return taskRepresentation.assignTaskToEmployee(id, task, pageable);
    }
}