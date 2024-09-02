package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.repository.PositionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PositionHistoryService {
    private final PositionHistoryRepository positionHistoryRepository;

    @Transactional
    public PositionHistory getPositionById(Long id) {
        return positionHistoryRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Запись об отпуске по данному id: %s не найден", id)));
    }

    @Transactional
    public PositionHistory createPositionHistory(PositionHistory positionHistory) {
        PositionHistory position = positionHistoryRepository.findByEmployeeAndPositionAndStartDate(
                positionHistory.getEmployee(),
                positionHistory.getPosition(),
                positionHistory.getStartDate()).orElseThrow(()
                -> new NoSuchElementException("Данная запись уже сущесвует") );
        return positionHistoryRepository.save(positionHistory);
    }

    @Transactional
    public void deletePositionHistoryById(Long id) {
        positionHistoryRepository.delete(getPositionById(id));
    }
}
