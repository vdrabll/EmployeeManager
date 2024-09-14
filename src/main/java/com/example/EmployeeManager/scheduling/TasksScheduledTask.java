package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TasksScheduledTask {
    private final TaskRepository taskRepository;

    @Transactional
    @Scheduled(cron = "0 0 9 * * *")
    public void taskCheck() {
        taskRepository.findAll()
                .stream()
                .filter(task -> task.getDeadline().isAfter(LocalDate.now()))
                .forEach(task -> task.setStatus(TaskStatus.EXPIRED));
    }
}
