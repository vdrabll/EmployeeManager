package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.EmployeeManager.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Department penzaDepartment;
    private Department moskowDepartment;

    private Employee chief;
    private Employee employee;



    @BeforeEach
    void setUp() {

        employee = employeeRepository.save(Employee.builder()
                        .fullName( "Иванов Петр Петрович")
                        .email("example@sber.ru")
                        .build());
        chief =  employeeRepository.save(Employee.builder()
                        .fullName( "Иванов Иван Петрович")
                        .email("example@yandex.ru")
                        .build());

        moskowDepartment = departmentRepository.save(Department.builder()
                .name("ЕгарТех")
                .location("Москва")
                .employees(Collections.emptyList())
                .build());
        penzaDepartment = departmentRepository.save(Department.builder()
                .name("СберТех")
                .location("Пенза")
                .employees(List.of(employee, chief))
                .build());

    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void getDepartmentById() {
        Department moskowDepartmentById = departmentService.getDepartmentById(moskowDepartment.getId());
        assertNotNull(moskowDepartmentById);
        assertEquals(moskowDepartment.getId(), moskowDepartmentById.getId());

        Department penzaDepartmentById = departmentService.getDepartmentById(penzaDepartment.getId());
        assertNotNull(penzaDepartmentById);
        assertEquals(penzaDepartment.getId(), penzaDepartmentById.getId());
    }

    @Test
    void getAll() {
        Page<Department> departments = departmentService.getAll(Pageable.unpaged());
        assertNotNull(departments);
        assertTrue(departments.getContent().size() == 2);
    }

    @Test
    void save() {
        Department newDepartment = departmentService.save(Department.builder()
                .name("АльфаТех")
                .location("Москва")
                .employees(Collections.emptyList())
                .build());

        Department departmentById = departmentService.getDepartmentById(3L);
        assertNotNull(departmentById);
        assertEquals(newDepartment.getName(), departmentById.getName());
    }

    @Test
    void updateDepartmentById() {
        Department newData = Department.builder()
                .name("google")
                .location("Москва")
                .build();
        departmentService.updateDepartmentById(1L, newData);

        Department departmentById = departmentService.getDepartmentById(1L);
        assertNotNull(departmentById);
        assertEquals(newData.getName(), departmentById.getName());
    }


    @Test
    void delete() {
        departmentService.delete(moskowDepartment);
        assertThrows(NoSuchElementException.class,() -> departmentService.getDepartmentById(moskowDepartment.getId()));
        assertTrue(departmentService.getAll(Pageable.unpaged()).getSize() == 1);
    }

    @Test
    void getAllEmployeesFromDepartment() {
        Page<Employee> allEmployeesFromDepartment = departmentService.getAllEmployeesFromDepartment(1L, Pageable.unpaged());
        assertEquals(allEmployeesFromDepartment.stream().toList().size(), 1);

        Page<Employee> allEmployees = departmentService.getAllEmployeesFromDepartment(2L, Pageable.unpaged());
        assertNotNull(allEmployees);
        assertTrue(allEmployees.stream().toList().size() == 1);

    }

    @Test
    void addEmployeeToDepartment() {
        Employee newEmployee = employeeRepository.save(Employee.builder()
                .fullName("Алексеев Акакий Тавридиев")
                .isWorkingNow(true)
                .email("exam@sber.ru")
                .build());
        departmentService.addEmployeeToDepartment(penzaDepartment.getId(),newEmployee.getId());
        departmentService.getDepartmentById(penzaDepartment.getId()).getEmployees().forEach(employee1 -> System.out.println(employee1.getFullName()));

        assertEquals(penzaDepartment.getEmployees().size(), 2);
    }

    @Test
    void removeEmployeeFromDepartment() {

    }
}