package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.ScheduleDTO;
import com.example.EmployeeManager.entity.Schedule;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMapper {
    public ScheduleDTO toDto(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO(schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getLocation());
        return dto;
    }

    public Schedule fromDto(ScheduleDTO dto) {
        Schedule schedule = Schedule.builder().date(dto.getDate()).startTime(dto.getStartTime()).endTime(dto.getEndTime()).location(dto.getLocation()).build();
        return schedule;
    }
}
