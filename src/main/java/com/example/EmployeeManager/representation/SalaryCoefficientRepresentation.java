package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.returnDTO.SalaryCoefficientReturnDTO;
import com.example.EmployeeManager.entity.SalaryCoefficients;
import com.example.EmployeeManager.service.SalaryCoefficientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalaryCoefficientRepresentation {
    private final SalaryCoefficientsService salaryCoefficientsService;

    public Page<SalaryCoefficientReturnDTO> getAllCoefficients() {
        return salaryCoefficientsService.getAllCoefficients(Pageable.unpaged()).map(this::toDTO);
    }

    public SalaryCoefficientReturnDTO toDTO(SalaryCoefficients data) {
        return new SalaryCoefficientReturnDTO(data.getId(), data.getAdvancePercentage(), data.getBonusPercentage());
    }
}
