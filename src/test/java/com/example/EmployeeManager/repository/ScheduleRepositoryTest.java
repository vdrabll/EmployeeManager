package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.*;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.LocationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class ScheduleRepositoryTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    private Schedule schedule;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().fullName( "Иванов Петр Петрович").email("example@sber.ru").build();
        schedule = Schedule.builder()
                .employee(employee)
                .date(new Date(2024, 2, 1))
                .startTime( new Time(9))
                .endTime(new Time(16))
                .location(LocationType.OFFICE)
                .build();
        scheduleRepository.save(schedule);
    }

    @AfterEach
    void tearDown() {
        scheduleRepository.deleteAll();
    }

    @Test
    void findAllByEmployee() {
        List testSchedule = List.of(schedule);
        List<Schedule> salaryHistories = scheduleRepository.findAllByEmployee(employee);
        assertNotNull(salaryHistories);
        assertThat(salaryHistories).isEqualTo(testSchedule);

    }
}