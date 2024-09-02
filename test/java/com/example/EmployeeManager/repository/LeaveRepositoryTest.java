package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class LeaveRepositoryTest {

    @Autowired
    private LeaveRepository leaveRepository;
    private Leave vacationLeave;
    private Leave sickLeave;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().fullName( "Иванов Петр Петрович").email("example@sber.ru").build();
        vacationLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.VACATION)
                .employee(employee)
                .startDate(new Date(2022, 2,1))
                .endDate(new Date(2022, 2,15))
                .build();

        sickLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.SICK)
                .employee(employee)
                .startDate(new Date(2022, 4,1))
                .endDate(new Date(2022, 5,15))
                .build();
        leaveRepository.save(vacationLeave);
        leaveRepository.save(sickLeave);
    }

    @AfterEach
    void tearDown() {
        leaveRepository.deleteById(vacationLeave.getId());
    }

    @Test
    void findByEmployeeTest() {
        List testLeaves = List.of(vacationLeave, sickLeave);
        List<Leave> leaves = leaveRepository.findByEmployee(employee);
        assertNotNull(leaves);
        assertThat(leaves).isEqualTo(testLeaves);
    }

    @Test
    void findAllByEndDateBetweenTest() {
        List<Leave> testLeaves = new ArrayList<Leave>();
        testLeaves.add(sickLeave);
        List<Leave> leaves = leaveRepository.findAllByEndDateBetween(new Date(2022,1,1), new Date(2022,12,31));
        assertNotNull(leaves);
        assertThat(leaves).isEqualTo(testLeaves);
    }

}