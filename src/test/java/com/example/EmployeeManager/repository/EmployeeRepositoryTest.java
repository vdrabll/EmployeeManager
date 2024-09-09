package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Role;
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
    @Autowired
    private RoleRepository roleRepository;
    private Employee chief;
    private Employee employee;
    private Role roleEmployee;
    private Role roleChief;
    private Pageable pageable;



    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        roleChief = roleRepository.save(new Role(AuthRole.CHIEF));
        roleEmployee = roleRepository.save(new Role(AuthRole.EMPLOYEE));
        chief = Employee.builder()
                .role(roleChief)
                .isWorkingNow(true)
                .fullName( "Иванов Петр Петрович")
                .email("example@sber.ru")
                .build();
        employeeRepository.save(chief);
        employee = Employee.builder().
                role(roleEmployee)
                .isWorkingNow(true)
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
        List<Employee> employees = List.of(chief, employee);
        List<Employee> employeeByIsWorkingNowEquals = employeeRepository.findAllByIsWorkingNowEquals(true, pageable).stream().toList();
        assertThat(employeeByIsWorkingNowEquals).isNotEmpty();
        assertThat(employeeByIsWorkingNowEquals).isEqualTo(employees);
    }

}