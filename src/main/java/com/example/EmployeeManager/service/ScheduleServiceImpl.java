package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.enums.LocationType;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.ScheduleRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeService employeeService;

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
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

    @Override
    public void createDailySchedule() {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atTime(9, 0);
        LocalDateTime endTime = today.atTime(18, 0);
        List<Employee> employees = employeeService.getAllWorkingEmployees(Pageable.unpaged()).toList();
        employees.forEach(employee -> {
            Schedule schedule = createSchedule(Schedule.builder()
                    .date(today)
                    .startTime(startTime)
                    .endTime(endTime)
                    .employee(employee)
                    .location(LocationType.OFFICE).build());
            employee.getSchedule().add(schedule);
        });
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
