package com.example.EmployeeManager.representation;


import com.example.EmployeeManager.dto.PositionHistoryDTO;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.service.interfaces.PositionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionHistoryRepresentation {
    private final PositionHistoryService positionHistoryService;

    public PositionHistoryDTO getPositionHistoryById(Long id) {
        return toDTO(positionHistoryService.getPositionById(id));
    }

    public PositionHistoryDTO createPositionHistory(PositionHistoryDTO positionHistory) {
        return toDTO(positionHistoryService.createPositionHistory(fromDTO(positionHistory)));
    }

    public void deletePositionHistoryById(Long id) {
        positionHistoryService.deletePositionHistoryById(id);
    }

    public PositionHistoryDTO toDTO(PositionHistory positionHistory) {
        PositionHistoryDTO dto = new PositionHistoryDTO(positionHistory.getStartDate(), positionHistory.getEndDate());
        return dto;
    }

    public  PositionHistory fromDTO(PositionHistoryDTO dto) {
        PositionHistory positionHistory = PositionHistory.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        return positionHistory;
    }
}
