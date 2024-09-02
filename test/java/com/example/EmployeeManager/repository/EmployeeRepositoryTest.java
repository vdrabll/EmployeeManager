package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.*;
import com.example.EmployeeManager.enums.AuthRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;

@DataJpaTest()
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    private Employee chief;
    private Employee employee;
    private Role roleEmployee;
    private Role roleChief;



    @BeforeEach
    void setUp() {
        roleChief = roleRepository.save(new Role(AuthRole.CHIEF));
        roleEmployee = roleRepository.save(new Role(AuthRole.EMPLOYEE));
        chief = Employee.builder()
                .role(roleChief)
                .fullName( "Иванов Петр Петрович")
                .email("example@sber.ru")
                .build();
        employeeRepository.save(chief);
        employee = Employee.builder().
                role(roleEmployee)
                .fullName( "Иванов Иван Петрович")
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
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        List<Employee> employeeByIsWorkingNowEquals = employeeRepository.findAllByIsWorkingNowEquals(false);
        assertThat(employeeByIsWorkingNowEquals).isNotEmpty();
        assertThat(employeeByIsWorkingNowEquals).isEqualTo(employees);
    }

}