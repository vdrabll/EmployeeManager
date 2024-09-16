package com.example.EmployeeManager.representation;


import com.example.EmployeeManager.dto.createDTO.PositionHistoryCreateDTO;
import com.example.EmployeeManager.dto.returnDTO.PositionHistoryReturnDTO;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.service.interfaces.PositionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionHistoryRepresentation {
    private final PositionHistoryService positionHistoryService;

    public PositionHistoryReturnDTO getPositionHistoryById(Long id) {
        return toDTO(positionHistoryService.getPositionById(id));
    }

    public PositionHistoryReturnDTO createPositionHistory(PositionHistoryCreateDTO positionHistory) {
        return toDTO(positionHistoryService.createPositionHistory(fromDTO(positionHistory)));
    }

    public void deletePositionHistoryById(Long id) {
        positionHistoryService.deletePositionHistoryById(id);
    }

    public PositionHistoryReturnDTO toDTO(PositionHistory positionHistory) {
        return new PositionHistoryReturnDTO(positionHistory.getId(),    positionHistory.getStartDate(), positionHistory.getEndDate());
    }

    public  PositionHistory fromDTO(PositionHistoryCreateDTO dto) {
        return PositionHistory.builder()
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .build();
    }
}
