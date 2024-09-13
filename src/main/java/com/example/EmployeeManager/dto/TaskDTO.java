package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String name;
    private String description;
    private TaskPriority priority;
    private LocalDate deadline;
    private Duration estimate;
    private TaskStatus status;
    private String type;

}
