package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.RoleRepository;
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

        Department departmentById = departmentService.getDepartmentById(newDepartment.getId());
        assertNotNull(departmentById);
        assertEquals(newDepartment.getName(), departmentById.getName());
        assertEquals(newDepartment.getId(), departmentById.getId());
        assertEquals(departmentService.getAll(Pageable.unpaged()).getSize(), 3);
        departmentService.getAll(Pageable.unpaged()).forEach(department -> System.out.println(department.getName()));
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
        Page<Employee> allEmployeesFromDepartment = departmentService.getAllEmployeesFromDepartment(moskowDepartment.getId(), Pageable.unpaged());
        assertEquals(allEmployeesFromDepartment.stream().toList().size(), 0);

        Page<Employee> allEmployees = departmentService.getAllEmployeesFromDepartment(penzaDepartment.getId(), Pageable.unpaged());
        assertNotNull(allEmployees);
        assertEquals(allEmployees.stream().toList().size(), 2);

    }

    @Test
    void addEmployeeToDepartment() {
        Department testDepartment = departmentRepository.save(Department.builder()
                .name("завод")
                .location("пенза")
                .build());
        Employee testEmployee = employeeRepository.save(Employee.builder()
                .fullName("olegsey")
                .email("exam@sber.ru")
                .build());

        Department department = departmentService.getDepartmentById(penzaDepartment.getId());
        departmentService.addEmployeeToDepartment(department.getId(), testEmployee.getId());
        assertNotNull(department);
        assertNotNull(testEmployee.getId());
        assertNotNull(penzaDepartment.getId());
        assertNotNull(department.getId());
        assertEquals(penzaDepartment.getName(), department.getName());
        assertEquals(penzaDepartment.getLocation(), department.getLocation());
        assertEquals(department.getEmployees().size(), 2);
        departmentService.addEmployeeToDepartment(penzaDepartment.getId(),testEmployee.getId());
        departmentService.getDepartmentById(penzaDepartment.getId()).getEmployees().forEach(employee1 -> System.out.println(employee1.getFullName()));
        assertEquals(departmentService.getDepartmentById(penzaDepartment.getId()).getEmployees().size(), 3);
    }

    @Test
    void removeEmployeeFromDepartment() {
        departmentService.removeEmployeeFromDepartment( penzaDepartment.getId(), employee.getId());
        departmentService.getAllEmployeesFromDepartment(penzaDepartment.getId(), Pageable.unpaged()).forEach(employee1 -> System.out.println(employee1.getFullName()));
        assertTrue(departmentService.getDepartmentById(penzaDepartment.getId()).getEmployees().size() == 1);

    }
}