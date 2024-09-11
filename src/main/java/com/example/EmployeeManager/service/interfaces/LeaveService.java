package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveService {

    Leave getLeaveById(Long id);

    Leave createLeave(Leave leave);

    void deleteLeaveById(Long id);

    void rescheduleLeave(Long id, Leave leave);

    Page<Leave> getAllByEmployee(Long employeeId, Pageable pageable);
}
