package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.*;
import com.example.EmployeeManager.enums.SalaryType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class SalaryHistoryRepositoryTest {
    @Autowired
    private SalaryHistoryRepository salaryHistoryRepository;
    private SalaryHistory salary;
    private SalaryHistory bonus;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().fullName( "Иванов Петр Петрович").email("example@sber.ru").build();
        bonus = SalaryHistory.builder()
                .employee(employee)
                .salaryDate(new Date(2023, 2, 21))
                .amount(BigDecimal.valueOf(16000))
                .type(SalaryType.BONUS)
                .build();

        salary = SalaryHistory.builder()
                .employee(employee)
                .salaryDate(new Date(2023, 2, 21))
                .amount(BigDecimal.valueOf(16000))
                .type(SalaryType.BONUS)
                .build();
        salaryHistoryRepository.saveAll(List.of(bonus,salary));
    }

    @AfterEach
    void tearDown() {
        salaryHistoryRepository.deleteAll();
    }

    @Test
    void findAllByEmployee() {
        List testSalary = List.of(bonus, salary);
        List<SalaryHistory> salaryHistories = salaryHistoryRepository.findAllByEmployee(employee);
        assertNotNull(salaryHistories);
        assertThat(salaryHistories).isEqualTo(testSalary);
        assertThat(salaryHistories.size()).isNotEqualTo(1);
    }
}