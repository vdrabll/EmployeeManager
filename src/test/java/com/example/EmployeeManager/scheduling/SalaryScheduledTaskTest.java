package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.dto.returnDTO.SalaryCoefficientReturnDTO;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.PositionHistoryRepository;
import com.example.EmployeeManager.repository.PositionRepository;
import com.example.EmployeeManager.representation.SalaryCoefficientRepresentation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class SalaryScheduledTaskTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;
    private PositionHistory positionHistory;
    private Position position;

    @Autowired
    private PositionHistoryRepository positionHistoryRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private SalaryCoefficientRepresentation salaryCoefficientRepresentation;

    private SalaryCoefficientReturnDTO coefficients;

    @Transactional
    @BeforeEach
    void setUp() {
        position = positionRepository.save(Position.builder().name("Продавец").grade((short) 6).salary(BigDecimal.valueOf(20000)).build());
        positionHistory = positionHistoryRepository.save(PositionHistory.builder().position(position).endDate(LocalDate.of(2019, 1,1)).startDate(LocalDate.of(2020, 1,1)).build());
        employee = employeeRepository.save(Employee.builder()
                .role(AuthRole.EMPLOYEE)
                .fullName("Янкова Алла Вячаславовна")
                .positionHistoryList(List.of(positionHistory))
                .email("alla@sber.ru")
                .build());

        coefficients = new SalaryCoefficientReturnDTO(1L, BigDecimal.valueOf(0.30), BigDecimal.valueOf(0.40));
    }

    @Transactional
    @AfterEach
    void tearDown() {
        positionHistoryRepository.deleteAll();
        positionRepository.deleteAll();
    }


    @Test
    void calculateAdvance() {
        BigDecimal data = SalaryScheduledTask.calculateAdvance(employee, coefficients.advancePercentage());
        System.out.println(data);
        Assertions.assertEquals(0, data.compareTo(BigDecimal.valueOf(6000)));

    }

    @Test
    void calculateSalary() {
        BigDecimal data = SalaryScheduledTask.calculateSalary(employee, coefficients.advancePercentage());
        System.out.println(data);
        Assertions.assertEquals(0, data.compareTo(BigDecimal.valueOf(14000)));
    }

    @Test
    void calculateBonus() {
        BigDecimal data = SalaryScheduledTask.calculateBonus(employee, coefficients.bonusPercentage());
        System.out.println(data);
        Assertions.assertEquals(0, data.compareTo(BigDecimal.valueOf(8000)));
    }
}