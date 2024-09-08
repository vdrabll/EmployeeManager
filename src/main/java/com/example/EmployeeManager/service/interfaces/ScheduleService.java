package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Schedule;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule);

    void deleteScheduleById(Long id);

    Schedule updateSchedule(Long id, Schedule schedule);

}
