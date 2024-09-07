package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.enums.LocationType;
import com.example.EmployeeManager.service.EmployeeService;
import com.example.EmployeeManager.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleTask {
    private final ScheduleService scheduleService;
    private final EmployeeService employeeService;

    // в начале каждого года нужно создавать расписание сотрудника по умолчанию офис и как обычно
    @Scheduled(cron = "0 0 * * *")
    public void createSchedule() {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atTime(9, 0);
        LocalDateTime endTime = today.atTime(18, 0);
        List<Employee> employees = employeeService.getAllWorkingEmployees();
        employees.forEach(employee -> employee.getSchedule().add(Schedule.builder()
                                .date(today)
                                .startTime(startTime)
                                .endTime(endTime)
                                .employee(employee)
                                .location(LocationType.OFFICE).build()));
    }
}
