package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.LeaveDTO;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.service.interfaces.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class LeaveRepresentation {
    private final LeaveService leaveService;

    public LeaveDTO getLeaveById(Long id) {
        return toDto(leaveService.getLeaveById(id));
    }

    public LeaveDTO createLeave(@RequestBody LeaveDTO leave) {
        return toDto(leaveService.createLeave(fromDto(leave)));
    }

    public void deleteLeaveById(Long id) {
        leaveService.deleteLeaveById(id);
    }

    public void rescheduleLeave( Long id,  LeaveDTO leave) {
        leaveService.rescheduleLeave(id, fromDto(leave));
    }

    public Page<LeaveDTO> getAllByEmployee(Long id, Pageable pageable) {
        return leaveService.getAllByEmployee(id, pageable).map(this::toDto);
    }

    public LeaveDTO toDto(Leave leave) {
        LeaveDTO dto = new LeaveDTO(
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getType(),
                leave.getStatus());
        return dto;
    }

    public Leave fromDto(LeaveDTO dto) {
        Leave leave = Leave.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .type(dto.getType())
                .status(dto.getStatus())
                .build();
        return leave;
    }
}
