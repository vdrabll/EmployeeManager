package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.enums.AuthRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest()
@ActiveProfiles("test")
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee chief;
    private Employee employee;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();

        chief = Employee.builder()
                .role(AuthRole.CHIEF)
                .isWorkingNow(true)
                .fullName( "Иванов Алексей Петрович")
                .email("example@sber.ru")
                .build();
        employeeRepository.save(chief);
        employee = Employee.builder().
                role(AuthRole.EMPLOYEE)
                .isWorkingNow(true)
                .fullName( "Аров Иван Иванович")
                .email("example@yandex.ru")
                .build();
        employeeRepository.save(employee);
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void findEmployeeByIsWorkingNowEquals() {
        List<Employee> employees = List.of(chief, employee);
        List<Employee> employeeByIsWorkingNowEquals = employeeRepository.findAllByIsWorkingNowTrue(pageable).stream().toList();
        assertThat(employeeByIsWorkingNowEquals).isNotEmpty();
        assertThat(employeeByIsWorkingNowEquals).isEqualTo(employees);
    }

}