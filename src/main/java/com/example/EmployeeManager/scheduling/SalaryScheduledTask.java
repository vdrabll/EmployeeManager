package com.example.EmployeeManager.scheduling;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.SalaryType;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.SalaryCoefficientsService;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    /**
     *  Advance payment
     *  Этот метод отвечает за выплату аванса. Планировщик срабатывает каждый пятый день месяца. Коэффициент аванса
     *  берется из базы данных с помощью метода получаени коэфициента в текущем году. Далее для всех, работающих в данный
     *  момент сотрудников создается запись в таблице Salary-History. Сумма вычисляется в функции calculateSalary.
     */
    @Transactional
    @Scheduled(cron = "0 0 15 5 * * ")
    public void paymentAdvance() {
        BigDecimal advancePercentage = salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).stream().toList().get(0).getAdvancePercentage();
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


    /**
     * Salary Payment
     *   Этот метод отвечает за выплату аванса. Планировщик срабатывает каждый 20 день месяца.
     *   Сумма вычисляется в функции calculateSalary.
     */
    @Transactional
    @Scheduled(cron = "0 0 15 20 * * ")
    public void paymentSalary() {
        BigDecimal advancePercentage = salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).toList().get(0).getAdvancePercentage();
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(LocalDate.now())
                        .amount(calculateAdvance(employee,advancePercentage))
                        .type(SalaryType.SALARY)
                        .build()));
    }

    /**
     * Bonus Payment
     *   Этот метод отвечает за выплату аванса. Планировщик срабатывает каждый 25 день месяца.
     *   Сумма вычисляется в функции calculateBonus.
     */
    @Transactional
    @Scheduled(cron = "0 0 15 25 * * ")
    public void paymentBonus() {
        BigDecimal bonusPercentage = salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).toList().get(0).getBonusPercentage();
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                salaryHistoryService.createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(LocalDate.now())
                        .amount(calculateBonus(employee, bonusPercentage))
                        .type(SalaryType.BONUS)
                        .build()));
    }

    /**
     * Этот метод отвечает за вычисление суммы аванса.
     *   Из входного параметра сотрудника мы получаем его текущую позицию и размер зарплаты, далее умножаем ее на
     *   коэффициент аванса и полученную сумму возвращаем.
     */

    public static BigDecimal calculateAdvance(Employee employee, BigDecimal advancePercentage) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        if (!employeePositions.isEmpty()) {
            Position position = employeePositions.get(employeePositions.size() - 1).getPosition();
            return position.getSalary().multiply(advancePercentage);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Этот метод отвечает за вычисление суммы зарплаты.
     *   Из входного параметра сотрудника мы получаем его текущую позицию и размер зарплаты, далее мы получаем размер уже
     *   выплаченного аванса и отнимаем его из общего оклада. Полученную сумму возвращаем.
     */
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

    /**
     * Этот метод отвечает за вычисление суммы ежемесячного бонуса.
     *   Из входного параметра сотрудника мы получаем его текущую позицию и размер зарплаты, далее мы умножаем сумму
     *   оклада на коэфициент выплаты бонуса.
     */
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
