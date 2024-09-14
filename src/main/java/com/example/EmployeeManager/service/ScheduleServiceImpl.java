package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.ScheduleRepository;
import com.example.EmployeeManager.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public  Schedule createSchedule(Schedule schedule) {
        if (scheduleRepository.findByEmployee_IdAndDate(schedule.getEmployee().getId(), schedule.getDate()).isEmpty()) {
            return scheduleRepository.save(schedule);
        } else {
            log.error("Record with {} already exists", schedule.getId());
            throw new RecordExistException(String.valueOf(schedule.getId()));
        }
    }

    @Transactional(readOnly = true)
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Запись о расписании по данному id: %s не найдена", id)));
    }

    @Transactional(readOnly = true)
    public Page<Schedule> getScheduleOfEmployee(Long employeeId, Pageable pageable) {
        return scheduleRepository.findAllByEmployee_Id(employeeId, pageable);
    }

    @Transactional
    public Schedule updateSchedule(Long id, Schedule schedule) {
        Schedule scheduleById = getScheduleById(id);
        scheduleById.setDate(schedule.getDate());
        scheduleById.setLocation(schedule.getLocation());
        scheduleById.setStartTime(schedule.getStartTime());
        scheduleById.setEndTime(schedule.getEndTime());
        return scheduleById;
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        if (scheduleRepository.findById(id).isPresent()) {
            scheduleRepository.delete(getScheduleById(id));
        } else {
            throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
        }
    }
}
