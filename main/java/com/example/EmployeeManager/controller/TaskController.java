package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(description = "Returns task by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(description = "Create task", method = "POST")
    @PostMapping public Task saveTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @Operation(description = "Update task by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @PutMapping("update/task/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @Operation(description = "Delete task by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @DeleteMapping("delete/task/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @Operation(description = "Returns all tasks of employee by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employee/{id}")
    public List<Task> getAllTasksOfEmployee(@PathVariable Long id) {
        return taskService.getAllTasksOfEmployee(id);
    }

    @Operation(description = "Assign task to employee by giving id", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true),
            @Parameter(name = "task", in = ParameterIn.PATH, description = "Unique identifier of task", required = true)
    })
    @PostMapping("/employee/{id}/task/{task}")
    public List<Task> assignTaskToEmployee(@PathVariable Long id, @RequestBody Task task) {
        return taskService.assignTaskToEmployee(id, task);
    }
}