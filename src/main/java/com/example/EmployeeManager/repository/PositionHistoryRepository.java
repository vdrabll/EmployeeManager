package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.PositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface PositionHistoryRepository extends JpaRepository<PositionHistory, Long> {
    Optional<PositionHistory> findByEmployee_IdAndPosition_IdAndStartDate(Long employeeId, Long positionId, Date startDate);
}
