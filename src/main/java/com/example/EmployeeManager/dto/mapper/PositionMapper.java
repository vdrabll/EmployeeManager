package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.PositionDTO;
import com.example.EmployeeManager.entity.Position;
import org.springframework.stereotype.Service;

@Service
public class PositionMapper {
    PositionDTO toDto(Position position) {
        PositionDTO dto = new PositionDTO(position.getGrade(), position.getSalary(), position.getName());
        return dto;
    }

    Position fromDto(PositionDTO dto) {
        Position position = Position.builder()
                .grade(dto.getGrade())
                .salary(dto.getSalary())
                .name(dto.getName())
                .build();
        return position;
    }
}
