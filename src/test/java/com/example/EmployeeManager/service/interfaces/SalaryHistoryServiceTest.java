package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.dto.returnDTO.SalaryCoefficientReturnDTO;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.SalaryType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.PositionHistoryRepository;
import com.example.EmployeeManager.repository.PositionRepository;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.scheduling.SalaryScheduledTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionHistoryRepository positionHistoryRepository;
    private PositionHistory positionHistory;
    private Position position;
    private SalaryHistory salary;
    private SalaryHistory bonus;
    private Employee employee;
    SalaryCoefficientReturnDTO coefficients;

    @BeforeEach
    void setUp() {
        position = positionRepository.save(Position.builder().name("Продавец").grade((short) 6).salary(BigDecimal.valueOf(20000)).build());
        positionHistory = positionHistoryRepository.save(PositionHistory.builder().position(position).endDate(LocalDate.of(2019, 1,1)).startDate(LocalDate.of(2020, 1,1)).build());
        employee = employeeRepository.save(Employee.builder()
                .role(AuthRole.EMPLOYEE)
                .fullName("Янкова Алла Вячаславовна")
                .positionHistoryList(List.of())
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
                .type(SalaryType.SALARY)
                .build());

        coefficients = new SalaryCoefficientReturnDTO(1L, BigDecimal.valueOf(0.30), BigDecimal.valueOf(0.40));

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
        history.forEach(salaryHistory -> System.out.println(salaryHistory.getType()));
        history.forEach(salaryHistory -> System.out.println(salaryHistory.getAmount()));
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

    @Test
    void calculateAdvance() {
        BigDecimal data = salaryHistoryService.calculateAdvance(employee, coefficients.advancePercentage());
        System.out.println(data);
        Assertions.assertEquals(0, data.compareTo(BigDecimal.valueOf(6000)));

    }

    @Test
    void calculateSalary() {
        BigDecimal data = salaryHistoryService.calculateSalary(employee, coefficients.advancePercentage());
        System.out.println(data);
        Assertions.assertEquals(0, data.compareTo(BigDecimal.valueOf(14000)));
    }

    @Test
    void calculateBonus() {
        BigDecimal data = salaryHistoryService.calculateBonus(employee, coefficients.bonusPercentage());
        System.out.println(data);
        Assertions.assertEquals(0, data.compareTo(BigDecimal.valueOf(8000)));
    }
}