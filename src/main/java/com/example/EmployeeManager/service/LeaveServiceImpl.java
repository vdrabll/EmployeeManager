package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.LeaveRepository;
import com.example.EmployeeManager.service.interfaces.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;


    @Transactional
    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись об отпуске по данному id: %s не найден", id)));
    }

   @Transactional
   public Leave createLeave(Leave leave) {
       List leaves = leaveRepository.findByEmployeeAndEndDate(leave.getEmployee(), leave.getEndDate()).orElseThrow(()
               -> new NoSuchElementException("Запись об отпуске на данную дату для сотрудника уже создана"));
       return leaveRepository.save(leave);
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
        Employee employeeById = employeeRepository.getReferenceById(employeeId);
        return leaveRepository.findByEmployee(employeeById, pageable);

    }
}
