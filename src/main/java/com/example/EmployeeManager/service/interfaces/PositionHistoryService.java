package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.PositionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PositionHistoryService {

    PositionHistory getPositionById(Long id);

    PositionHistory createPositionHistory(PositionHistory positionHistory);

    void deletePositionHistoryById(Long id);

    Page<PositionHistory> getAllByEmployeeId(Long employeeId, Pageable pageable);
}
