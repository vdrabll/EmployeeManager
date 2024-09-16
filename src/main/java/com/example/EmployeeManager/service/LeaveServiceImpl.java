package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.exceptions.InvalidLeaveDateExeption;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.LeaveRepository;
import com.example.EmployeeManager.service.interfaces.LeaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;

   @Transactional
   public Leave createLeave(Leave leave) {
       if (leave.getStartDate().isBefore(LocalDate.now())) {
           throw new InvalidLeaveDateExeption("Нельзя создать запись об отпуске на прошедшую дату");
       }
       if (!leaveRepository.existsByEmployee_IdAndEndDate(leave.getEmployee().getId(), leave.getEndDate())) {
           return leaveRepository.save(leave);
       } else {
           log.error("Record with {} already exists", leave.getId());
           throw new RecordExistException(String.valueOf(leave.getEndDate()));
       }
   }

    @Transactional(readOnly = true)
    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Запись об отпуске по данному id: %s не найдена", id)));
    }

    @Transactional(readOnly = true)
    public Page<Leave> getAllByEmployee(Long employeeId, Pageable pageable) {
        return leaveRepository.findByEmployee_Id(employeeId, pageable);
    }

    @Transactional
    public void rescheduleLeave(Long id, Leave leave) {
        if (leave.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidLeaveDateExeption("Нельзя перенести отпуск на прошедшую дату.");
        }
        Leave leaveById = getLeaveById(id);
        leaveById.setType(leave.getType());
        leaveById.setStartDate(leave.getStartDate());
        leaveById.setEndDate(leave.getEndDate());
    }

   @Transactional
    public void deleteLeaveById(Long id) {
       if (leaveRepository.findById(id).isPresent()) {
           leaveRepository.delete(getLeaveById(id));
       } else {
           throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
       }
   }
}
