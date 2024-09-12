package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.PositionHistoryDTO;
import com.example.EmployeeManager.entity.PositionHistory;
import org.springframework.stereotype.Service;

@Service
public class PositionHistoryMapper {
    public  PositionHistoryDTO toDto (PositionHistory positionHistory) {
        PositionHistoryDTO dto = new PositionHistoryDTO(positionHistory.getStartDate(), positionHistory.getEndDate());
        return dto;
    }

    public  PositionHistory fromDto(PositionHistoryDTO dto) {
        PositionHistory positionHistory = PositionHistory.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        return positionHistory;
    }
}
