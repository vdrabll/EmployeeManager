package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Position;

public interface PositionService {

    Position getPositionById(Long id);

    Position createPosition(Position position);

    void deletePositionById(Long id);

    Position updatePosition(Long id, Position position);

}
