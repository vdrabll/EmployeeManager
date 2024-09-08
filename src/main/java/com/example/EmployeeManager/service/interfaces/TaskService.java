package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Task getTaskById(Long id);

    Page<Task> getAllTasks(Pageable pageable);

    Task saveTask(Task task);

    Task updateTask(Long id, Task task);

    void deleteTask(Long id);

    Page<Task> getAllTasksOfEmployee(Long employeeId, Pageable pageable);

    Page<Task> assignTaskToEmployee(Long employeeId, Task task, Pageable pageable);
}
