package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.PositionDTO;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.service.interfaces.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionRepresentation {
    private final PositionService positionService;

    public PositionDTO getPositionById(Long id) {
        return toDTO(positionService.getPositionById(id));
    }

    public PositionDTO createPosition(Position position) {
        return toDTO(positionService.createPosition(position));
    }

    public void deletePositionById(Long id) {
        positionService.deletePositionById(id);
    }

    public PositionDTO updatePosition( Long id, PositionDTO position) {
        return toDTO(positionService.updatePosition(id, fromDTO(position)));
    }

    PositionDTO toDTO(Position position) {
        PositionDTO dto = new PositionDTO(position.getGrade(), position.getSalary(), position.getName());
        return dto;
    }

    Position fromDTO(PositionDTO dto) {
        Position position = Position.builder()
                .grade(dto.getGrade())
                .salary(dto.getSalary())
                .name(dto.getName())
                .build();
        return position;
    }
}
