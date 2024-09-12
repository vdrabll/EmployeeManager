package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.TaskDTO;
import com.example.EmployeeManager.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
    TaskDTO toDto(Task task) {
        TaskDTO dto = new TaskDTO(task.getName(), task.getDescription(), task.getPriority(), task.getDeadline(), task.getStatus(), task.getType());
        return dto;
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
                .build();
        return task;
    }
}
