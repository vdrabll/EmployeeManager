package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TasksScheduledTask {
    private final TaskServiceImpl taskService;
    private final TaskRepository taskRepository;

    @Scheduled(cron = "0 0 9 * * *")
    public void taskCheck() {
        List<Task> allTasks = taskRepository.findAll();
        allTasks.forEach(task ->  checkIfNotExpired(task));
    }

    private void checkIfNotExpired(Task task) {
        if (task.getDeadline().after(new Date())) {
            task.setStatus(TaskStatus.EXPIRED);
        }
    }
}
