package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.enums.AuthRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;

@DataJpaTest()
@ActiveProfiles("test")
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;
    private Department firtsDepartment;
    private Employee chief;
    private Employee employee;
    private Role roleEmployee;
    private Role roleChief;

    @BeforeEach
    public  void setUp() {
        roleChief = roleRepository.save(new Role(AuthRole.CHIEF));
        roleEmployee = roleRepository.save(new Role(AuthRole.EMPLOYEE));
        chief = Employee.builder().role(roleEmployee).fullName( "Иванов Петр Петрович").email("example@sber.ru").build();
        employee = Employee.builder().role(roleChief).fullName( "Иванов Иван Петрович").email("example@yandex.ru").build();
        firtsDepartment = new Department("ТехСбер", "Байдукова 172", new LinkedList<Employee>() );
        firtsDepartment.getEmployees().add(chief);
        firtsDepartment.getEmployees().add(employee);
        departmentRepository.save(firtsDepartment);
    }

    @AfterEach
    public void dropDepartments() {
        departmentRepository.deleteAll();
    }

//    @Test
//    public void getDepartmentChiefTest() {
//        Employee departmentChief = departmentRepository.getDepartmentChief(firtsDepartment.getId());
//        assertThat(departmentChief).isNotNull();
//        assertThat(departmentChief.getFullName().equals(chief.getFullName()));
//    }
}