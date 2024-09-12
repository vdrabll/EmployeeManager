package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.LeaveDTO;
import com.example.EmployeeManager.entity.Leave;
import org.springframework.stereotype.Service;

@Service
public class LeaveMapper {
    private LeaveDTO toDto(Leave leave) {
        LeaveDTO dto = new LeaveDTO(
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getType(),
                leave.getStatus());
        return dto;
    }

    private Leave fromDto(LeaveDTO dto) {
        Leave leave = Leave.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .type(dto.getType())
                .status(dto.getStatus())
                .build();
        return leave;
    }
}
