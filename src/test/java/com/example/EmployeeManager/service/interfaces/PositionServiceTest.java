package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.repository.PositionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PositionServiceTest {
     @Autowired
     private PositionService positionService;
     @Autowired
     private PositionRepository positionRepository;

     private Position developerPosition;
     private Position HRPosition;

    @BeforeEach
    void setUp() {
        developerPosition = positionRepository.save(Position.builder()
                .grade((short) 12)
                .salary(BigDecimal.valueOf(70000)).
                name("Ведущий iOS разработчик").
                build());
        HRPosition = positionRepository.save(Position.builder()
                .grade((short) 10)
                .salary(BigDecimal.valueOf(70000)).
                name("Старший специалист по подбору персонала").
                build());
    }

    @AfterEach
    void tearDown() {
        positionRepository.deleteAll();
    }

    @Test
    void getPositionById() {
        Position position = positionService.getPositionById(developerPosition.getId());
        assertNotNull(position);
        assertEquals(developerPosition.getName(), position.getName());
        assertEquals(developerPosition.getGrade(), position.getGrade());
        assertTrue(developerPosition.getSalary().compareTo(position.getSalary()) == 0);

        Position hrPosition = positionService.getPositionById(HRPosition.getId());
        assertNotNull(hrPosition);
        assertEquals(HRPosition.getName(), hrPosition.getName());
        assertEquals(HRPosition.getGrade(), hrPosition.getGrade());
        assertTrue(HRPosition.getSalary().compareTo(hrPosition.getSalary()) == 0);
    }

    @Test
    void createPosition() {
        Position position = positionService.createPosition(Position.builder().salary(BigDecimal.valueOf(50000)).grade((short) 8).name("Художник").build());
        assertNotNull(position);
        Position createdPosition = positionService.getPositionById(position.getId());
        assertNotNull(createdPosition);
        assertEquals(position.getName(), createdPosition.getName());
        assertEquals(position.getGrade(), createdPosition.getGrade());
        assertTrue(position.getSalary().compareTo(createdPosition.getSalary()) == 0);
        assertEquals(positionRepository.findAll().size(), 3);
    }

    @Test
    void deletePositionById() {
        positionService.deletePositionById(developerPosition.getId());
        assertThrows(NoSuchElementException.class, () -> positionService.getPositionById(developerPosition.getId()));
    }

    @Test
    void updatePosition() {
        Position newData = Position.builder()
                .grade((short) 10)
                .salary(BigDecimal.valueOf(50000)).
                name("Младший iOS разработчик").
                build();
        Position position = positionService.updatePosition(developerPosition.getId(), newData);
        assertNotNull(position);
        assertEquals(position.getName(), newData.getName());
        assertEquals(position.getGrade(), newData.getGrade());
        assertTrue(position.getSalary().compareTo(newData.getSalary()) == 0);

    }
}