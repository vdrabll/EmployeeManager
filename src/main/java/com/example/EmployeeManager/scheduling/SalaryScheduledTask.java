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
     *  Этот метод отвечает за выплату аванса. Планировщик срабатывает каждый пятый день месяца.
     */
    @Transactional
    @Scheduled(cron = "0 0 15 5 * * ")
    public void paymentAdvance() {
        salaryHistoryService.createAdvancePayment();
    }

    /**
     * Salary Payment
     *   Этот метод отвечает за выплату зарплаты. Планировщик срабатывает каждый 20 день месяца.
     */
    @Transactional
    @Scheduled(cron = "0 0 15 20 * * ")
    public void paymentSalary() {
        salaryHistoryService.createSalaryPayment();
    }

    /**
     * Bonus Payment
     *   Этот метод отвечает за выплату аванса. Планировщик срабатывает каждый 25 день месяца.
     */
    @Transactional
    @Scheduled(cron = "0 0 15 25 * * ")
    public void paymentBonus() {
        salaryHistoryService.createBonusPayment();
    }

}
