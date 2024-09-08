package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.SalaryType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.service.EmployeeServiceImpl;
import com.example.EmployeeManager.service.SalaryHistoryServiceImpl;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SalaryScheduledTask {
    private final EmployeeRepository employeeRepositiry;
    private final SalaryHistoryService salaryHistoryService;
    private final SalaryHistoryRepository salaryHistoryRepository;

    private final LocalDate salaryDate = LocalDate.now();
    private BigDecimal advancePercentage = new BigDecimal("0.3");
    private BigDecimal bonusPercentage = new BigDecimal("0.45");



    @Scheduled(cron = "0 0 15 5 * * ")
    public void paymentAdvance() {

        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(salaryDate)
                        .amount(calculateSalary(employee))
                        .type(SalaryType.ADVANCE)
                        .build()));
    }

    @Scheduled(fixedRate = 5000)
    public void paymentSalary() {
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(salaryDate)
                        .amount(calculateAdvance(employee))
                        .type(SalaryType.SALARY)
                        .build()));
    }

    @Scheduled(cron = "0 0 15 25 * * ")
    public void paymentBonus() {
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(salaryDate)
                        .amount(calculateBonus(employee)) //
                        .type(SalaryType.BONUS)
                        .build()));
    }


    private BigDecimal calculateAdvance(Employee employee) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        Position position = employeePositions.get(employeePositions.size() - 1).getPosition();

        return position.getSalary().multiply(advancePercentage);
    }

    private BigDecimal calculateSalary(Employee employee) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        Position position = employeePositions.get(employeePositions.size() - 1).getPosition();
        BigDecimal advance = position.getSalary().multiply(advancePercentage);

        return position.getSalary().subtract(advance);
    }

    private BigDecimal calculateBonus(Employee employee) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        Position position = employeePositions.get(employeePositions.size() - 1).getPosition();

        return position.getSalary().multiply(bonusPercentage);
    }
}
