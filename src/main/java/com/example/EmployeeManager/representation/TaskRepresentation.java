package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.TaskDTO;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class TaskRepresentation {
    private final TaskService taskService;

    public TaskDTO getTaskById(@PathVariable Long id) {
        return toDto(taskService.getTaskById(id));
    }

    TaskDTO toDto(Task task) {
        TaskDTO dto = new TaskDTO(task.getName(), task.getDescription(), task.getPriority(), task.getDeadline(), task.getEstimate(), task.getStatus(), task.getType());
        return dto;
    }

    public TaskDTO saveTask(TaskDTO task) {
        return toDto(taskService.saveTask(fromDto(task)));
    }

    public TaskDTO updateTask(Long id, TaskDTO task) {
        Task data = fromDto(task);
        data.setId(id);
        return toDto(taskService.updateTask(id, data));
    }

    public void deleteTask(Long id) {
        taskService.deleteTask(id);
    }

    public Page<TaskDTO> getAllTasksOfEmployee(Long id, Pageable pageable) {
        return taskService.getAllTasksOfEmployee(id, pageable).map(this::toDto);
    }

    public TaskDTO assignTaskToEmployee(Long id, TaskDTO task, Pageable pageable) {
        return toDto(taskService.assignTaskToEmployee(id, fromDto(task), pageable));
    }


    Task fromDto(TaskDTO dto) {
        Task task = Task.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .deadline(dto.getDeadline())
                .deadline(dto.getDeadline())
                .status(dto.getStatus())
                .type(dto.getType())
                .estimate(dto.getEstimate())
                .build();
        return task;
    }
}
