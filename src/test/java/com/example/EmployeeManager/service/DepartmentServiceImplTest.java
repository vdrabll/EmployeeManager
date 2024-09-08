package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.interfaces.DepartmentService;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;
    private Department moskowDepartment;
    private Department penzaDepartment;
    private Employee moskowEmployee;
    private Employee penzaEmployee;

    @BeforeEach
    void setUp() {
        moskowEmployee = Employee.builder()
                .fullName("Иванов Иван Иванович")
                .email("ivanov@gmail.com")
                .isWorkingNow(true)
                .build();
        penzaEmployee =  Employee.builder()
                .fullName("Иванов Петр Иванович")
                .email("ivanovPetr@gmail.com")
                .isWorkingNow(true)
                .build();
        employeeRepository.save(moskowEmployee);
        employeeRepository.save(penzaEmployee);

        moskowDepartment = Department.builder()
                .name("Moskow Tech")
                .location("Moskow")
                .employees(List.of(moskowEmployee))
                .build();
        penzaDepartment = Department.builder()
                .name("Penza Tech")
                .location("Penza")
                .employees(List.of(penzaEmployee))
                .build();
            departmentRepository.save(moskowDepartment);
            departmentRepository.save(penzaDepartment);
    }

    @AfterEach
    void tearDown() {
        departmentRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getDepartmentByIdTest() {
//        Department moskowDepartmentById = departmentService.getDepartmentById(Long.valueOf(moskowDepartment.getId()));
//        assertNotNull(moskowDepartmentById);
//        assertThat(moskowDepartmentById).isEqualTo(moskowDepartment);
//        Department penzaDepartmentById = departmentService.getDepartmentById(Long.valueOf(penzaDepartment.getId()));
//        assertNotNull(penzaDepartmentById);
//        assertThat(penzaDepartmentById).isEqualTo(penzaDepartment);
    }

    @Test
    void getAllTest() {

    }

    @Test
    void save() {
    }

    @Test
    void updateDepartmentById() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllEmployeesFromDepartment() {
    }

    @Test
    void addEmployeeToDepartment() {
    }

    @Test
    void removeEmployeeFromDepartment() {
    }
}