package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.enums.LocationType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.EmployeeServiceImpl;
import com.example.EmployeeManager.service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleTask {
    private final ScheduleServiceImpl scheduleService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeServiceImpl employeeServiceImpl;

    @Scheduled(cron = "0 0 0  * * MON-FRI")
    public void createSchedule() {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atTime(9, 0);
        LocalDateTime endTime = today.atTime(18, 0);
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(employee -> employee.getSchedule().add(Schedule.builder()
                                .date(today)
                                .startTime(startTime)
                                .endTime(endTime)
                                .employee(employee)
                                .location(LocationType.OFFICE).build()));
    }
}
