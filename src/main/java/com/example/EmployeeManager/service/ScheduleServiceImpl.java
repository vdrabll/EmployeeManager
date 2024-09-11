package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.ScheduleRepository;
import com.example.EmployeeManager.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись о расписании по данному id: %s не найдена", id)));
    }

    @Transactional
    public  Schedule createSchedule(Schedule schedule) {
        if (scheduleRepository.findByEmployee_IdAndDate(schedule.getEmployee().getId(), schedule.getDate()).isEmpty()) {
            return scheduleRepository.save(schedule);
        } else {
            throw new RecordExistException(String.valueOf(schedule.getId()));
        }
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        scheduleRepository.delete(getScheduleById(id));
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
    public Page<Schedule> getScheduleOfEmployee(Long employeeId, Pageable pageable) {
        return scheduleRepository.findAllByEmployee_Id(employeeId, pageable);
    }
}
