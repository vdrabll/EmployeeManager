package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Task getTaskById(Long id);

    Task saveTask(Task task);

    Task updateTask(Task task, Long id);

    void deleteTask(Long id);

    Page<Task> getAllTasksOfEmployee(Long id, Pageable pageable);

    Task assignTaskToEmployee(Long employeeId, Task task, Pageable pageable);

}
