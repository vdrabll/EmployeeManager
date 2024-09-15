package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.create.LeaveCreateDTO;
import com.example.EmployeeManager.dto.LeaveReturnDTO;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.service.interfaces.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveRepresentation {
    private final LeaveService leaveService;

    public LeaveReturnDTO getLeaveById(Long id) {
        return toDto(leaveService.getLeaveById(id));
    }

    public LeaveReturnDTO createLeave( LeaveCreateDTO leave) {
        return toDto(leaveService.createLeave(fromDto(leave)));
    }

    public void deleteLeaveById(Long id) {
        leaveService.deleteLeaveById(id);
    }

    public void rescheduleLeave(Long id, LeaveCreateDTO leave) {

        leaveService.rescheduleLeave(id, fromDto(leave));
    }

    public Page<LeaveReturnDTO> getAllByEmployee(Long id, Pageable pageable) {
        return leaveService.getAllByEmployee(id, pageable).map(this::toDto);
    }

    public LeaveReturnDTO toDto(Leave leave) {
        LeaveReturnDTO dto = new LeaveReturnDTO(
                leave.getId(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getType(),
                leave.getStatus());
        return dto;
    }

    public Leave fromDto(LeaveCreateDTO dto) {
        Leave leave = Leave.builder()
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .type(dto.type())
                .status(dto.status())
                .build();
        return leave;
    }
}
