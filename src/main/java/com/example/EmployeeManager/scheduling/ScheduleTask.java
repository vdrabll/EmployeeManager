package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.enums.LocationType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.EmployeeServiceImpl;
import com.example.EmployeeManager.service.ScheduleServiceImpl;
import com.example.EmployeeManager.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleTask {
    private final EmployeeServiceImpl employeeService;
    private final ScheduleService scheduleService;

    @Transactional
    @Scheduled(cron = "0 0 0  * * MON-FRI")
    public void createSchedule() {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atTime(9, 0);
        LocalDateTime endTime = today.atTime(18, 0);
        List<Employee> employees = employeeService.getAllEmployee(Pageable.unpaged()).toList();
        employees.forEach(employee -> {
            Schedule schedule = scheduleService.createSchedule(Schedule.builder()
                    .date(today)
                    .startTime(startTime)
                    .endTime(endTime)
                    .employee(employee)
                    .location(LocationType.OFFICE).build());
            employee.getSchedule().add(schedule);
        });
    }
}
