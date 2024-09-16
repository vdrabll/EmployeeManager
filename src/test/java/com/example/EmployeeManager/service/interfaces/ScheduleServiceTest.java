package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.LocationType;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.ScheduleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Schedule monday;
    private Schedule tuesday;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = employeeRepository.save( Employee.builder()
                .role(AuthRole.EMPLOYEE)
                .fullName("Люричева Лара Петровна")
                .email("cuty@sber.ru")
                .build());
        monday = scheduleRepository.save(Schedule.builder()
                .employee(employee)
                .date(LocalDate.now())
                .startTime( LocalDate.now().atTime(9,0))
                .endTime( LocalDate.now().atTime(16,0))
                .location(LocationType.OFFICE)
                .build());
        tuesday = scheduleRepository.save(Schedule.builder()
                .employee(employee)
                .date(LocalDate.of(2024, 3, 21))
                .startTime(LocalDate.now().atTime(9,0))
                .endTime( LocalDate.now().atTime(16,0))
                .location(LocationType.OFFICE)
                .build());
    }

    @AfterEach
    void tearDown() {
        scheduleRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void createSchedule() {
        Schedule newSchedule = scheduleService.createSchedule(Schedule.builder()
                .employee(employee)
                .date(LocalDate.of(2024, 2, 11))
                .startTime( LocalDate.now().atTime(9,0))
                .endTime( LocalDate.now().atTime(18,0))
                .location(LocationType.OFFICE)
                .build());
        Schedule savedSchedule = scheduleService.getScheduleById(newSchedule.getId());
        assertNotNull(savedSchedule);
        assertEquals(newSchedule.getId(), savedSchedule.getId());
        assertEquals(newSchedule.getEmployee().getId(), savedSchedule.getEmployee().getId());
        assertEquals(newSchedule.getDate(), savedSchedule.getDate());
        assertEquals(newSchedule.getStartTime(), savedSchedule.getStartTime());
        assertEquals(newSchedule.getEndTime(), savedSchedule.getEndTime());
        assertEquals(newSchedule.getLocation(), savedSchedule.getLocation());
    }

    @Test
    void deleteScheduleById() {
        scheduleService.deleteScheduleById(monday.getId());
        assertThrows(NotFoundException.class, () -> scheduleService.deleteScheduleById(monday.getId()));
    }

    @Test
    void updateSchedule() {
        Schedule newData = Schedule.builder()
                .employee(employee)
                .date(LocalDate.of(2024, 2, 11))
                .startTime( LocalDate.now().atTime(9,0))
                .endTime( LocalDate.now().atTime(18,0))
                .location(LocationType.OFFICE)
                .build();
        System.out.println(monday.getEndTime());
        scheduleService.updateSchedule(monday.getId(), newData);
        Schedule updatadSchedule = scheduleService.getScheduleById(monday.getId());
        System.out.println(updatadSchedule.getEndTime());
        assertEquals(updatadSchedule.getStartTime(), newData.getStartTime());
        assertEquals(updatadSchedule.getEndTime(), newData.getEndTime());
        assertEquals(updatadSchedule.getLocation(), newData.getLocation());

    }

    @Test
    void getScheduleOfEmployeeTest() {
        List<Schedule> scheduleOfEmployee = scheduleService.getScheduleOfEmployee(employee.getId(), Pageable.unpaged()).toList();
        assertEquals(2, scheduleOfEmployee.size());
    }

    @Test
    void getScheduleById() {
        Schedule scheduleById = scheduleService.getScheduleById(monday.getId());
        assertNotNull(scheduleById);
        assertEquals(monday.getDate(), scheduleById.getDate());
        assertEquals(monday.getStartTime(), scheduleById.getStartTime());
        assertEquals(monday.getEndTime(), scheduleById.getEndTime());
        assertEquals(monday.getLocation(), scheduleById.getLocation());
        assertThrows(NotFoundException.class, () -> scheduleService.getScheduleById(100L));
    }
}