package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.PositionHistory;

public interface PositionHistoryService {

    PositionHistory getPositionById(Long id);

    PositionHistory createPositionHistory(PositionHistory positionHistory);

    void deletePositionHistoryById(Long id);
}
