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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class EmployeeServiceTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    EmployeeService employeeService;
    private Employee chief;
    private Employee employee;
    private Department department;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        chief = Employee.builder()
                .isWorkingNow(true)
                .fullName("Иванов Алексей Петрович")
                .email("example@sber.ru")
                .build();
        employeeRepository.save(chief);
        employee = Employee.builder()
                .isWorkingNow(true)
                .fullName("Аров Иван Иванович")
                .email("example@yandex.ru")
                .build();
        employeeRepository.save(employee);
        department = departmentRepository.save(Department.builder()
                .name("ПензаТех")
                .location("Пенз 666")
                .employees(List.of(chief,employee))
                .build());

    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getEmployeeById() {
        Employee testEmployee = employeeService.getEmployeeById(employee.getId());
        Employee chiefEmployee = employeeService.getEmployeeById(chief.getId());

        assertNotNull(chiefEmployee);
        assertNotNull(chiefEmployee);
        assertEquals(employee.getId(), testEmployee.getId());
        assertEquals(employee.getFullName(), testEmployee.getFullName());
        assertEquals(employee.getEmail(), testEmployee.getEmail());
        assertEquals(chief.getId(), chiefEmployee.getId());
        assertEquals(chief.getFullName(), chiefEmployee.getFullName());
        assertEquals(chief.getEmail(), chiefEmployee.getEmail());
    }

    @Test
    void getAllEmployee() {
        List<Employee> allEmployee = employeeService.getAllEmployee(pageable).stream().toList();

        assertEquals(allEmployee.size(), 2);
        assertEquals(allEmployee.get(1).getFullName(), employee.getFullName());
        assertEquals(allEmployee.get(1).getEmail(), employee.getEmail());
        assertEquals(allEmployee.get(0).getFullName(), chief.getFullName());
        assertEquals(allEmployee.get(0).getEmail(), chief.getEmail());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> allEmployee.get(2));
    }

    @Test
    void addEmployee() {
        Employee newEmployee = employeeService.addEmployee(Employee.builder()
                .isWorkingNow(true)
                .fullName("Кручинина Алиса Николаевна")
                .email("alice@sber.ru")
                .build());
        assertNotNull(newEmployee);
        assertNotNull(employeeService.getEmployeeById(newEmployee.getId()));
        assertEquals(employeeService.getEmployeeById(newEmployee.getId()).getFullName(), newEmployee.getFullName());
    }

    @Test
    void updateEmployee() {
        Employee newEmployee = Employee.builder()
                .isWorkingNow(true)
                .fullName("Кручинина Алиса Николаевна")
                .email("alice@sber.ru")
                .build();
        Employee updated =  employeeService.updateEmployee(1L, newEmployee);
        System.out.println(updated.getFullName());
        employeeService.getAllEmployee(Pageable.unpaged()).forEach(employee1 -> System.out.println(employee1.getFullName()));
        assertEquals(updated.getFullName(), newEmployee.getFullName());
        assertEquals(updated.getEmail(), newEmployee.getEmail());
        assertNotEquals(employeeService.getEmployeeById(chief.getId()).getFullName(), "Иванов Алексей Петрович");
    }

    @Test
    void dismissEmployee() {
        Employee dismissedEmployee = employeeService.dismissEmployee(employee.getId());
        assertNotEquals(dismissedEmployee.getIsWorkingNow(), true);
    }

    @Test
    void getAllWorkingEmployees() {
        Page<Employee> allWorkingEmployees = employeeService.getAllWorkingEmployees(Pageable.unpaged());
        assertNotEquals(allWorkingEmployees.getContent().size(), 0);
        assertEquals(allWorkingEmployees.getTotalElements(), 2);
        assertTrue(allWorkingEmployees.stream().noneMatch(employee1 -> employee1.getIsWorkingNow().equals(false)));
    }

    @Test
    void getAllDismissedEmployees() {
        Page<Employee> allDismissedEmployees = employeeService.getAllDismissedEmployees(pageable);
        assertEquals(allDismissedEmployees.getContent().size(), 0);
        assertEquals(allDismissedEmployees.getTotalElements(), 0);
        assertTrue(allDismissedEmployees.stream().noneMatch(employee1 -> employee1.getIsWorkingNow().equals(true)));
    }

    @Test
    void getAllEmployeesByDepartment() {
        List<Employee> employees =  employeeService.getAllEmployeesByDepartment(department.getId(), pageable).toList();
        assertEquals(employees.size(), 2);
        assertNotEquals(employees.size(), 0);
    }
}