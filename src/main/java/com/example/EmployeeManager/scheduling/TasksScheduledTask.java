package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TasksScheduledTask {
    private final TaskService taskService;

    /**
     * Метод проверки задач.
     * Планировщик срабатывает в 9 утра каждого дня.
     */
    @Transactional
    @Scheduled(cron = "0 0 9 * * *")
    public void taskCheck() {
       taskService.checkTasks();
    }
}
