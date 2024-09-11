package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.PositionHistoryRepository;
import com.example.EmployeeManager.repository.PositionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PositionHistoryServiceTest {
    @Autowired
    private PositionHistoryRepository positionHistoryRepository;
    @Autowired
    private PositionHistoryService positionHistoryService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRepository positionRepository;

    private Employee employee;
    private Position firstPosition;
    private Position secondPosition;
    private PositionHistory intern;
    private PositionHistory worker;


    @BeforeEach
    void setUp() {
        employee = employeeRepository.save( Employee.builder()
                .fullName("Cтоматин Петр Петрович")
                .email("example@sber.ru")
                .build());

        firstPosition = positionRepository.save(Position.builder().grade((short)4)
                .name("Младший специалист")
                .salary(BigDecimal.valueOf(20000))
                .build());
        secondPosition = positionRepository.save(Position.builder().grade((short)6)
                .name("Средний специалист")
                .salary(BigDecimal
                        .valueOf(40000))
                .build());


        intern = positionHistoryRepository.save(PositionHistory.builder()
                .position(firstPosition)
                .employee(employee)
                .startDate(LocalDate.of(2020, 2,1))
                .endDate(LocalDate.of(2020, 5,4))
                .build());
        worker = positionHistoryRepository.save(PositionHistory.builder()
                .position(secondPosition)
                .employee(employee)
                .startDate(LocalDate.of(2020, 5,4))
                .endDate(null)
                .build());

    }

    @AfterEach
    void tearDown() {
        positionHistoryRepository.deleteAll();
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Test
    void getPositionById() {
        Position internPosition = positionHistoryService.getPositionById(intern.getId()).getPosition();
        assertNotNull(internPosition);
        assertEquals(internPosition.getName(), intern.getPosition().getName());
        assertTrue(internPosition.getSalary().compareTo(intern.getPosition().getSalary()) == 0);
        assertEquals(internPosition.getGrade(), intern.getPosition().getGrade());
        assertThrows(NoSuchElementException.class, () -> positionHistoryService.getPositionById(22L).getPosition());
    }

    @Test
    void createPositionHistory() {
        PositionHistory newPositionHistory = positionHistoryRepository.save(PositionHistory.builder()
                .position(firstPosition)
                .employee(employee)
                .startDate(LocalDate.of(2019, 5,4))
                .endDate(LocalDate.of(2020, 9,1))
                .build());
        List<PositionHistory> allPositionHistories = positionHistoryRepository.findAll();
        assertEquals(3, allPositionHistories.size());
        assertEquals(newPositionHistory.getStartDate(), positionHistoryService.getPositionById(newPositionHistory.getId()).getStartDate());
        assertEquals(newPositionHistory.getEndDate(), positionHistoryService.getPositionById(newPositionHistory.getId()).getEndDate());

    }

    @Test
    void deletePositionHistoryById() {
        positionHistoryRepository.deleteById(intern.getId());
        positionHistoryRepository.findAll().forEach(positionHistory -> {
            System.out.println(positionHistory.getStartDate());
        });
        assertThrows(NoSuchElementException.class, () -> positionHistoryService.getPositionById(intern.getId()));
    }

    @Test
    void getAllByEmployeeId() {
        List<PositionHistory> allByEmployeeId = positionHistoryService.getAllByEmployeeId(employee.getId(), Pageable.unpaged()).toList();
        assertEquals(2, allByEmployeeId.size());
    }
}