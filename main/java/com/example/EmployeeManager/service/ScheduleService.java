package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись о расписании по данному id: %s не найдена", id)));
    }

    @Transactional
    public  Schedule createSchedule(Schedule schedule) {
        Schedule byEmployeeAndDate = scheduleRepository.findByEmployeeAndDate(
                schedule.getEmployee(),
                schedule.getDate())
                .orElseThrow(() -> new NoSuchElementException("Запись о депатраменте не найдена"));
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        scheduleRepository.delete(getScheduleById(id));
    }

    @Transactional
    public Schedule updateSchedule(Long id, Schedule schedule) {
        Schedule scheduleByIdById = getScheduleById(id);
        scheduleByIdById.setDate(schedule.getDate());
        scheduleByIdById.setLocation(schedule.getLocation());
        scheduleByIdById.setStartTime(schedule.getStartTime());
         scheduleByIdById.setEndTime(schedule.getEndTime());
        return scheduleByIdById;
    }

    @Transactional
    public List<Schedule> getScheduleOfEmployee(Long employeeId) {
        Employee employeeById = employeeRepository.getReferenceById(employeeId);
        return scheduleRepository.findAllByEmployee(employeeById);
    }
}
