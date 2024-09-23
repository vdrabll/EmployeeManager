package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.SalaryType;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    private final SalaryHistoryRepository salaryHistoryRepository;
    private final EmployeeRepository employeeRepositiry;
    private final SalaryCoefficientsService salaryCoefficientsService;


    @Transactional
    public SalaryHistory createSalaryHistory(SalaryHistory salary) {
        if (salaryHistoryRepository.findByEmployeeAndSalaryDateAndType(salary.getEmployee(), salary.getSalaryDate(), salary.getType()).isEmpty()) {
            return salaryHistoryRepository.save(salary);
        } else {
            log.error("Record with {} already exists", salary.getId());
            throw new RecordExistException(String.valueOf(salary.getId()));
        }
    }

    @Transactional(readOnly = true)
    public SalaryHistory getSalaryHistoryById(Long id) {
         return salaryHistoryRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Проект по данному id: %s не найден", id)));
    }

    @Transactional(readOnly = true)
    public Page<SalaryHistory> getSalaryHistoryOfEmployee(Long employeeId, Pageable pageable) {
        return salaryHistoryRepository.findAllByEmployee_Id(employeeId, pageable);
    }

    /**
     *  Advance payment
     *  Коэффициент аванса берется из базы данных с помощью метода получаения коэфициента в текущем году. Далее для всех, работающих в данный
     *  момент сотрудников создается запись в таблице Salary-History. Сумма вычисляется в функции calculateSalary.
     */
    @Override
    public void createAdvancePayment() {
        BigDecimal advancePercentage = salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).stream().toList().get(0).getAdvancePercentage();
        employeeRepositiry.findAll().forEach(employee ->
                createSalaryHistory(
                        SalaryHistory
                                .builder()
                                .employee(employee)
                                .salaryDate(LocalDate.now())
                                .amount(calculateSalary(employee, advancePercentage))
                                .type(SalaryType.ADVANCE)
                                .build()));
    }

    @Override
    public void createSalaryPayment() {
        BigDecimal advancePercentage = salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).toList().get(0).getAdvancePercentage();
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                createSalaryHistory(SalaryHistory.builder()
                        .employee(employee)
                        .salaryDate(LocalDate.now())
                        .amount(calculateAdvance(employee,advancePercentage))
                        .type(SalaryType.SALARY)
                        .build()));
    }

    @Override
    public void createBonusPayment() {
        BigDecimal bonusPercentage = salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).toList().get(0).getBonusPercentage();
        List<Employee> allWorkingEmployees = employeeRepositiry.findAll();
        allWorkingEmployees.forEach(employee ->
                createSalaryHistory(SalaryHistory.builder()
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
    @Override
    public BigDecimal calculateAdvance(Employee employee, BigDecimal advancePercentage) {
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
    @Override
    public BigDecimal calculateSalary(Employee employee, BigDecimal advancePercentage) {
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
    @Override
    public BigDecimal calculateBonus(Employee employee, BigDecimal bonusPercentage) {
        List<PositionHistory> employeePositions = employee.getPositionHistoryList();
        if (!employeePositions.isEmpty()) {
            Position position = employeePositions.get(employeePositions.size() - 1).getPosition();
            return position.getSalary().multiply(bonusPercentage);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
