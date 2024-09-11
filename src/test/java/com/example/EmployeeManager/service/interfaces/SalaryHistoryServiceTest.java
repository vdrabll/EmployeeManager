package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.SalaryType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class SalaryHistoryServiceTest {
    @Autowired
    private SalaryHistoryService salaryHistoryService;
    @Autowired
    private SalaryHistoryRepository salaryHistoryRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private SalaryHistory salary;
    private SalaryHistory bonus;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = employeeRepository.save(Employee.builder()
                .fullName("Янкова Алла Вячаславовна")
                .email("alla@sber.ru")
                .build());
        bonus = salaryHistoryRepository.save( SalaryHistory.builder()
                .employee(employee)
                .salaryDate(LocalDate.of(2022, 3, 20))
                .amount(BigDecimal.valueOf(16000))
                .type(SalaryType.BONUS)
                .build());
        salary = salaryHistoryRepository.save( SalaryHistory.builder()
                .employee(employee)
                .salaryDate(LocalDate.of(2022, 3, 20))
                .amount(BigDecimal.valueOf(16000))
                .type(SalaryType.BONUS)
                .build());
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        salaryHistoryRepository.deleteAll();
    }

    @Test
    void getSalaryHistoryById() {
        SalaryHistory salaryHistoryById = salaryHistoryService.getSalaryHistoryById(salary.getId());
        assertNotNull(salaryHistoryById);
        assertEquals(salary.getId(), salaryHistoryById.getId());
        assertEquals(salary.getEmployee().getId(), salaryHistoryById.getEmployee().getId());
        assertEquals(salary.getSalaryDate(), salaryHistoryById.getSalaryDate());
    }

    @Test
    void createSalaryHistory() {
        SalaryHistory newSalaryHistory = salaryHistoryRepository.save( SalaryHistory.builder()
                .employee(employee)
                .salaryDate(LocalDate.of(2022, 4, 20))
                .amount(BigDecimal.valueOf(16000))
                .type(SalaryType.BONUS)
                .build());
        Page<SalaryHistory> history = salaryHistoryService.getSalaryHistoryOfEmployee(employee.getId(), Pageable.unpaged());
        assertEquals(3, history.getTotalElements());
        assertNotEquals(2, history.getTotalElements());

    }

    @Test
    void getSalaryHistoryOfEmployee() {
        Page<SalaryHistory> history = salaryHistoryService.getSalaryHistoryOfEmployee(employee.getId(), Pageable.unpaged());
        assertEquals(2, history.getTotalElements());
        assertNotEquals(4, history.getTotalElements());
        assertTrue(history.stream().allMatch(h -> h.getEmployee().getId() == employee.getId()));
    }
}