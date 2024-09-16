package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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

    private Department penzaDepartment;
    private Department moskowDepartment;

    private Employee chief;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = employeeRepository.save(Employee.builder()
                        .role(AuthRole.EMPLOYEE)
                        .fullName( "Иванов Петр Петрович")
                        .email("example@sber.ru")
                        .build());
        chief =  employeeRepository.save(Employee.builder()
                        .role(AuthRole.CHIEF)
                        .fullName( "Иванов Иван Петрович")
                        .email("example@yandex.ru")
                        .build());

        moskowDepartment = departmentRepository.save(Department.builder()
                .name("ЕгарТех")
                .location("Москва")
                .employees(List.of())
                .build());
        moskowDepartment.setEmployees(List.of(employee));
        departmentRepository.save(moskowDepartment);

        penzaDepartment = departmentRepository.save(Department.builder()
                .name("СберТех")
                .location("Пенза")
                .employees(List.of())
                .build());
        penzaDepartment.setEmployees(List.of(chief));
        departmentRepository.save(penzaDepartment);
    }

    @AfterEach
    void tearDown() {
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
        assertEquals(2, departments.getContent().size());
    }

    @Test
    void save() {
        Department newDepartment = departmentService.save(Department.builder()
                .name("АльфаТех")
                .location("Москва")
                .employees(Collections.emptyList())
                .build());

        Department departmentById = departmentService.getDepartmentById(newDepartment.getId());
        assertNotNull(departmentById);
        assertEquals(newDepartment.getName(), departmentById.getName());
        assertEquals(newDepartment.getId(), departmentById.getId());
        assertEquals(departmentService.getAll(Pageable.unpaged()).getSize(), 3);
        departmentService.getAll(Pageable.unpaged()).forEach(department -> System.out.println(department.getName()));
    }

    @Test
    void updateDepartmentById() {
 // TODO: исправить
    }


    @Test
    void delete() {
        departmentService.delete(moskowDepartment);
        assertThrows(NotFoundException.class,() -> departmentService.getDepartmentById(moskowDepartment.getId()));
        assertEquals(1, departmentService.getAll(Pageable.unpaged()).getSize());
    }

    @Test
    void getAllEmployeesFromDepartment() {
        Page<Employee> allEmployeesFromDepartment = departmentService.getAllEmployeesFromDepartment(moskowDepartment.getId(), Pageable.unpaged());
        assertEquals(allEmployeesFromDepartment.stream().toList().size(), 1);

        Page<Employee> allEmployees = departmentService.getAllEmployeesFromDepartment(penzaDepartment.getId(), Pageable.unpaged());
        assertNotNull(allEmployees);
        assertEquals(allEmployees.stream().toList().size(), 1);

    }

    @Test
    void addEmployeeToDepartment() {
        Employee testEmployee = Employee.builder()
                .role(AuthRole.EMPLOYEE)
                .fullName("olegsey")
                .email("exam@sber.ru")
                .build();
        employeeRepository.save(testEmployee);
        penzaDepartment.getEmployees().forEach(employee1 -> System.out.println(employee1.getEmail()));
        departmentService.addEmployeeToDepartment(penzaDepartment.getId(), testEmployee.getId());
        Department updatedDepartment = departmentService.getDepartmentById(penzaDepartment.getId());
        assertEquals(updatedDepartment.getEmployees().size(), 2);
    }

    @Test
    void removeEmployeeFromDepartment() {
        departmentService.removeEmployeeFromDepartment( penzaDepartment.getId(), employee.getId());
        departmentService.getAllEmployeesFromDepartment(penzaDepartment.getId(), Pageable.unpaged()).forEach(employee1 -> System.out.println(employee1.getFullName()));
    }
}