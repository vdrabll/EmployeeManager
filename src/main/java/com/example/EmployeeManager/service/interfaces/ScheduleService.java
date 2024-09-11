package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    Schedule getScheduleById(Long id);

    Schedule createSchedule(Schedule schedule);

    void deleteScheduleById(Long id);

    Schedule updateSchedule(Long id, Schedule schedule);

    Page<Schedule> getScheduleOfEmployee(Long employeeId, Pageable pageable);

}
