package com.example.EmployeeManager.service;

import com.example.EmployeeManager.dto.TaskDTO;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.exceptions.InvalidLeaveDateExeption;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.LeaveRepository;
import com.example.EmployeeManager.service.interfaces.DepartmentService;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.LeaveService;
import com.example.EmployeeManager.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final TaskService taskService;


    @Transactional
    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись об отпуске по данному id: %s не найдена", id)));
    }

   @Transactional
   public Leave createLeave(Leave leave) {
       if (leave.getStartDate().isBefore(LocalDate.now())) {
           throw new InvalidLeaveDateExeption("Нельзя создать запись об отпуске на прошедшую дату");
       }
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
        leaveById.setStartDate(leave.getStartDate());
        leaveById.setEndDate(leave.getEndDate());
    }

    @Transactional
    public void createLeaveRequestTask(Long employeeId, Leave taskData) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        Employee chief = employeeService.getAllEmployee(Pageable.unpaged()).stream().filter(employee1 -> employee1.getRole().getId() == AuthRole.CHIEF).toList().get(0);
        Task task = Task.builder()
                .name(String.format("Назначить отпуск для $s",employee.getFullName()))
                .description(String.format("Желаемые даты: $s - $s, тип: $s", taskData.getStartDate(), taskData.getEndDate(), taskData.getType()))
                .type("Отпуск")
                .priority(TaskPriority.HIGH)
                .build();
        taskService.assignTaskToEmployee(chief.getId(), task, Pageable.unpaged());
    }

    @Transactional
    public Page<Leave> getAllByEmployee(Long employeeId, Pageable pageable) {
        Employee employeeById = employeeService.getEmployeeById(employeeId);
        return leaveRepository.findByEmployee(employeeById, pageable);

    }
}
