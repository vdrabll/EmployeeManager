package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.PositionHistoryRepository;
import com.example.EmployeeManager.service.interfaces.PositionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PositionHistoryServiceImpl implements PositionHistoryService {
    private final PositionHistoryRepository positionHistoryRepository;

    @Transactional
    public PositionHistory getPositionById(Long id) {
        return positionHistoryRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись об отпуске по данному id: %s не найден", id)));
    }

    @Transactional
    public PositionHistory createPositionHistory(PositionHistory positionHistory) {
        if (positionHistoryRepository.findByEmployee_IdAndPosition_IdAndStartDate(positionHistory.getEmployee().getId(),
                positionHistory.getPosition().getId(),
                positionHistory.getStartDate()).isEmpty()) {
            return positionHistoryRepository.save(positionHistory);
        } else {
            throw new RecordExistException(String.valueOf(positionHistory.getStartDate()));
        }
    }

    @Transactional
    public void deletePositionHistoryById(Long id) {
        positionHistoryRepository.delete(getPositionById(id));
    }

    @Transactional
    public Page<PositionHistory> getAllByEmployeeId(Long employeeId, Pageable pageable) {
        return positionHistoryRepository.findByEmployee_Id(employeeId, pageable);
    }

}
