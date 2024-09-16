package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TasksScheduledTask {
    private final TaskRepository taskRepository;

    /**
     * Метод проверки задач.
     * Планировщик срабатывает в 9 утра каждого дня. Функция проверяет все существующие в базе записи о задачах.
     * Фильтрует их по дате выполнения и если они меньше сегодняшнего дня, то меняет им статус.
     */
    @Transactional
    @Scheduled(cron = "0 0 9 * * *")
    public void taskCheck() {
        taskRepository.findAll()
                .stream()
                .filter(task -> task.getDeadline().isAfter(LocalDate.now()))
                .forEach(task -> task.setStatus(TaskStatus.EXPIRED));
    }
}
