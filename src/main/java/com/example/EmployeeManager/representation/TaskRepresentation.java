package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.TaskCreateDTO;
import com.example.EmployeeManager.dto.TaskReturnDTO;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRepresentation {
    private final TaskService taskService;

    public TaskReturnDTO getTaskById(Long id) {
        return toDto(taskService.getTaskById(id));
    }


    public TaskReturnDTO saveTask(TaskCreateDTO task) {
        return toDto(taskService.saveTask(fromDto(task)));
    }

    public TaskReturnDTO updateTask(Long id, TaskCreateDTO task) {
        Task data = fromDto(task);
        data.setId(id);
        return toDto(taskService.updateTask(data, id));
    }

    public void deleteTask(Long id) {
        taskService.deleteTask(id);
    }

    public Page<TaskReturnDTO> getAllTasksOfEmployee(Long id, Pageable pageable) {
        return taskService.getAllTasksOfEmployee(id, pageable).map(this::toDto);
    }

    public TaskReturnDTO assignTaskToEmployee(Long id, TaskCreateDTO task, Pageable pageable) {
        return toDto(taskService.assignTaskToEmployee(id, fromDto(task), pageable));
    }

    TaskReturnDTO toDto(Task task) {
        return TaskReturnDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priority(task.getPriority())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .type(task.getType())
                .build();
    }


    Task fromDto(TaskCreateDTO dto) {
        Task task = Task.builder()
                .name(dto.name())
                .description(dto.description())
                .priority(dto.priority())
                .deadline(dto.deadline())
                .status(dto.status())
                .type(dto.type())
                .build();
        return task;
    }
}
