package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.create.SalaryCoefficientCreateDTO;
import com.example.EmployeeManager.dto.SalaryCoefficientReturnDTO;
import com.example.EmployeeManager.entity.SalaryCoefficients;
import com.example.EmployeeManager.service.SalaryCoefficientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SalaryCoefficientRepresentation {
    private final SalaryCoefficientsService salaryCoefficientsService;

    public Page<SalaryCoefficientReturnDTO> getAllCoefficients() {
        return salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).map(this::toDTO);
    }

    public SalaryCoefficientReturnDTO createNewCoefficien(SalaryCoefficientCreateDTO salaryCoefficients) {
        return toDTO(salaryCoefficientsService.createNewCoefficien(fromDTO(salaryCoefficients)));
    }

    public SalaryCoefficientReturnDTO getCoefficientOfYear(LocalDate date) {
        return toDTO(salaryCoefficientsService.getCoefficientOfYear(date));
    }

    public SalaryCoefficientReturnDTO toDTO(SalaryCoefficients data) {
        return new SalaryCoefficientReturnDTO(data.getId(), data.getYear(), data.getAdvancePercentage(), data.getBonusPercentage());
    }

    public SalaryCoefficients fromDTO(SalaryCoefficientCreateDTO data) {
        return SalaryCoefficients.builder().year(data.date()).advancePercentage(data.advancePercentage()).bonusPercentage(data.bonusPercentage()).build();
    }

}
