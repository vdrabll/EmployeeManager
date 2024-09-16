package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.createDTO.PositionCreateDTO;
import com.example.EmployeeManager.dto.returnDTO.PositionReturnDTO;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.service.interfaces.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionRepresentation {
    private final PositionService positionService;

    public PositionReturnDTO getPositionById(Long id) {
        return toDTO(positionService.getPositionById(id));
    }

    public PositionReturnDTO createPosition(Position position) {
        return toDTO(positionService.createPosition(position));
    }

    public void deletePositionById(Long id) {
        positionService.deletePositionById(id);
    }

    public PositionReturnDTO updatePosition(Long id, PositionCreateDTO position) {
        return toDTO(positionService.updatePosition(id, fromDTO(position)));
    }

    PositionReturnDTO toDTO(Position position) {
        return new PositionReturnDTO(position.getId(), position.getGrade(), position.getSalary(), position.getName());
    }

    Position fromDTO(PositionCreateDTO dto) {
        return Position.builder()
                .grade(dto.grade())
                .salary(dto.salary())
                .name(dto.name())
                .build();
    }
}
