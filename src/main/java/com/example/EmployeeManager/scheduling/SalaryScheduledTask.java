package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.*;
import com.example.EmployeeManager.enums.SalaryType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.service.SalaryCoefficientsService;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SalaryScheduledTask {
    private final EmployeeRepository employeeRepositiry;
    private final SalaryHistoryService salaryHistoryService;
    private final SalaryCoefficientsService salaryCoefficientsService;


    @Transactional
    @Scheduled(cron = "0 0 15 5 * * ")
    public void paymentAdvance() {
        BigDecimal advancePercentage = salaryCoefficientsService.getCoefficientOfYear(LocalDate.now()).getAdvancePercentage();
        employeeRepositiry.findAll().forEach(employee ->
                salaryHistoryService.createSalaryHistory(
                        SalaryHistory
                                .builder()
                                .employee(employee)
                                .salaryDate(LocalDate.now())
                                .amount(calculateSalary(employee, advancePercentage))
                                .type(SalaryType.ADVANCE)
                                .build()));
    }

    @Transactional
    @Scheduled(cron = "0 0 15 20 * * ")
    public void paymentSalary() {
        BigDecimal advancePercentage = salaryCoefficientsService.getCoefficientOfYear(LocalDate.now()).getAdvancePercentage();
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(LocalDate.now())
                        .amount(calculateAdvance(employee,advancePercentage))
                        .type(SalaryType.SALARY)
                        .build()));
    }

    @Transactional
    @Scheduled(cron = "0 0 15 25 * * ")
    public void paymentBonus() {
        BigDecimal bonusPercentage = salaryCoefficientsService.getCoefficientOfYear(LocalDate.now()).getBonusPercentage();
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(LocalDate.now())
                        .amount(calculateBonus(employee, bonusPercentage))
                        .type(SalaryType.BONUS)
                        .build()));
    }


    public static BigDecimal calculateAdvance(Employee employee, BigDecimal advancePercentage) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        if (!employeePositions.isEmpty()) {
            Position position = employeePositions.get(employeePositions.size() - 1).getPosition();
            return position.getSalary().multiply(advancePercentage);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal calculateSalary(Employee employee, BigDecimal advancePercentage) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        if (!employeePositions.isEmpty()) {
            Position position = employeePositions.get(employeePositions.size() - 1).getPosition();
            BigDecimal advance = position.getSalary().multiply(advancePercentage);
            return position.getSalary().subtract(advance);
        }  else {
            return BigDecimal.ZERO;
        }

    }

    public static BigDecimal calculateBonus(Employee employee, BigDecimal bonusPercentage) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        if (!employeePositions.isEmpty()) {
            Position position = employeePositions.get(employeePositions.size() - 1).getPosition();
            return position.getSalary().multiply(bonusPercentage);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
