package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.LeaveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class LeaveServiceTest {
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Leave vacationLeave;
    private Leave sickLeave;
    private Employee employee;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        employee = Employee.builder()
                .isWorkingNow(true)
                .fullName( "Иванов Петр Петрович")
                .email("example@sber.ru")
                .build();
        employeeRepository.save(employee);

        vacationLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.VACATION)
                .employee(employee)
                .startDate(LocalDate.of(2022, 2,1))
                .endDate(LocalDate.of(2022, 2,1))
                .build();

        sickLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.SICK)
                .employee(employee)
                .startDate(LocalDate.of(2022, 7,1))
                .endDate(LocalDate.of(2022, 7,15))
                .build();
        leaveRepository.save(vacationLeave);
        leaveRepository.save(sickLeave);
    }

    @AfterEach
    void tearDown() {
        leaveRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getLeaveById() {
        Leave leave = leaveRepository.findById(vacationLeave.getId()).orElseThrow();
        assertNotNull(leave);
        assertEquals(leave.getStartDate(), vacationLeave.getStartDate());
        assertEquals(leave.getEndDate(), vacationLeave.getEndDate());
        assertEquals(leave.getStatus(), vacationLeave.getStatus());
        assertEquals(leave.getType(), vacationLeave.getType());

        Leave anotherLeave = leaveRepository.findById(sickLeave.getId()).orElseThrow();
        assertNotNull(anotherLeave);
        assertEquals(anotherLeave.getStartDate(), sickLeave.getStartDate());
        assertEquals(anotherLeave.getEndDate(), sickLeave.getEndDate());
        assertEquals(anotherLeave.getStatus(), sickLeave.getStatus());
        assertEquals(anotherLeave.getType(), sickLeave.getType());
    }

    @Test
    void createLeave() {
        Leave newLeave = Leave.builder()
                .status(LeaveStatus.ON_REVIEW)
                .type(LeaveType.UNPAID)
                .employee(employee)
                .startDate(LocalDate.of(2022, 7,14))
                .endDate(LocalDate.of(2022, 7,15))
                .build();
    }

    @Test
    void deleteLeaveById() {
        leaveRepository.deleteById(vacationLeave.getId());
        Page<Leave> allByEmployee = leaveService.getAllByEmployee(employee.getId(), pageable);
        assertTrue(allByEmployee.stream().noneMatch(leave -> leave.getId().equals(vacationLeave.getId())));

    }

    @Test
    void rescheduleLeave() {
        Leave newLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.SICK)
                .employee(employee)
                .startDate(LocalDate.of(2022, 7,10))
                .endDate(LocalDate.of(2022, 7,15))
                .build();
        leaveService.rescheduleLeave(sickLeave.getId(), newLeave);
        Optional<Leave> byId = leaveRepository.findById(sickLeave.getId());
        assertNotNull(leaveRepository.findById(sickLeave.getId()));

        assertEquals(newLeave.getEndDate(), byId.get().getEndDate());
        assertEquals(newLeave.getStartDate(), byId.get().getStartDate());
        assertEquals(newLeave.getStatus(), byId.get().getStatus());
        assertEquals(newLeave.getType(), byId.get().getType());
    }

    @Test
    void getAllByEmployee() {
        List<Leave> leaves =  leaveService.getAllByEmployee(employee.getId(), Pageable.unpaged()).toList();
        assertEquals(leaves.size(),2);
        leaves.forEach(leave -> System.out.println(leave.getStartDate()));
        assertTrue(leaves.stream().allMatch(leave -> leave.getEmployee().getId() == employee.getId()));
    }
}