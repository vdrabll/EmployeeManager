package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select e from employee e join public.employees_departments ed on e.id = ed.employee_id where e.role_id = 'CHIEF' and ed.department_id = #{#departmentId}", nativeQuery = true)
    Optional<Employee> findChiefByDepartmentId(@Param("departmentId") Long departmentId);

    Optional<Department> findByName(String name);

    boolean existsByName(String name);

}
