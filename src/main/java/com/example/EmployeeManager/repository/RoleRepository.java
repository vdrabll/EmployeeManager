package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.enums.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, AuthRole> {
}
