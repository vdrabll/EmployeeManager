package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByNameAndSalary(String name, BigDecimal salary);
}
