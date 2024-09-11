package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.LeaveRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeService employeeService;


    @Transactional
    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись об отпуске по данному id: %s не найден", id)));
    }

   @Transactional
   public Leave createLeave(Leave leave) {
       if (leaveRepository.findByEmployeeAndEndDate(leave.getEmployee(), leave.getEndDate(), Pageable.unpaged()).isEmpty()) {
           return leaveRepository.save(leave);
       } else {
           throw new RecordExistException(String.valueOf(leave.getEndDate()));
       }
   }

   @Transactional
    public void deleteLeaveById(Long id) {
        leaveRepository.delete(getLeaveById(id));
   }

    @Transactional
    public void rescheduleLeave(Long id, Leave leave) {
        Leave leaveById = getLeaveById(id);
        leaveById.setType(leave.getType());
        leaveById.setStatus(leave.getStatus());
        leaveById.setStartDate(leave.getStartDate());
        leaveById.setEndDate(leave.getEndDate());
    }

    @Transactional
    public Page<Leave> getAllByEmployee(Long employeeId, Pageable pageable) {
        Employee employeeById = employeeService.getEmployeeById(employeeId);
        return leaveRepository.findByEmployee(employeeById, pageable);

    }
}
