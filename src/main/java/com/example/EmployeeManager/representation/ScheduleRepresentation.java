package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.ScheduleDTO;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class ScheduleRepresentation {
    private final ScheduleService scheduleService;

    public ScheduleDTO getScheduleById(Long id) {
        return toDto(scheduleService.getScheduleById(id));
    }

    public void deleteScheduleById(Long id) {
        scheduleService.deleteScheduleById(id);
    }

    public ScheduleDTO updateSchedule(Long id, ScheduleDTO schedule) {
        Schedule data = fromDto(schedule);
        data.setId(id);
        return toDto(scheduleService.updateSchedule(id, data));
    }

    public Page<ScheduleDTO> getScheduleOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return scheduleService.getScheduleOfEmployee(id, pageable).map(this::toDto);
    }

    public ScheduleDTO toDto(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO(schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getLocation());
        return dto;
    }

    public Schedule fromDto(ScheduleDTO dto) {
        Schedule schedule = Schedule.builder().date(dto.getDate()).startTime(dto.getStartTime()).endTime(dto.getEndTime()).location(dto.getLocation()).build();
        return schedule;
    }
}
