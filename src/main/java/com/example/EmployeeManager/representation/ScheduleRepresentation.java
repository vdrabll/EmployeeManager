package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.create.ScheduleCreateDTO;
import com.example.EmployeeManager.dto.ScheduleReturnDTO;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleRepresentation {
    private final ScheduleService scheduleService;

    public ScheduleReturnDTO getScheduleById(Long id) {
        return toDto(scheduleService.getScheduleById(id));
    }

    public void deleteScheduleById(Long id) {
        scheduleService.deleteScheduleById(id);
    }

    public ScheduleReturnDTO updateSchedule(Long id, ScheduleCreateDTO schedule) {
        Schedule data = fromDto(schedule);
        data.setId(id);
        return toDto(scheduleService.updateSchedule(id, data));
    }

    public Page<ScheduleReturnDTO> getScheduleOfEmployee(Long id, Pageable pageable) {
        return scheduleService.getScheduleOfEmployee(id, pageable).map(this::toDto);
    }

    public ScheduleReturnDTO toDto(Schedule schedule) {
        ScheduleReturnDTO dto = new ScheduleReturnDTO(schedule.getId(), schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getLocation());
        return dto;
    }

    public Schedule fromDto(ScheduleCreateDTO dto) {
        return Schedule.builder()
                .date(dto.date())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .location(dto.location())
                .build();
    }
}
