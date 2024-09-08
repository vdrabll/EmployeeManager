package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.*;
import com.example.EmployeeManager.enums.LocationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
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
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        employee = Employee.builder().fullName( "Иванов Петр Петрович").email("example@sber.ru").build();
        schedule = Schedule.builder()
                .employee(employee)
                .date(LocalDate.now())
                .startTime( LocalDate.now().atTime(9,0))
                .endTime( LocalDate.now().atTime(16,0))
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
        List<Schedule> salaryHistories = scheduleRepository.findAllByEmployee(employee, pageable).toList();
        assertNotNull(salaryHistories);
        assertThat(salaryHistories).isEqualTo(testSchedule);

    }
}