package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.PositionHistory;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.PositionHistoryRepository;
import com.example.EmployeeManager.service.interfaces.PositionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionHistoryServiceImpl implements PositionHistoryService {
    private final PositionHistoryRepository positionHistoryRepository;

    @Transactional
    public PositionHistory createPositionHistory(PositionHistory positionHistory) {
        if (positionHistoryRepository.findByEmployee_IdAndPosition_IdAndStartDate(positionHistory.getEmployee().getId(),
                positionHistory.getPosition().getId(),
                positionHistory.getStartDate()).isEmpty()) {
            return positionHistoryRepository.save(positionHistory);
        } else {
            log.error("Record with {} already exists", positionHistory.getId());
            throw new RecordExistException(String.valueOf(positionHistory.getStartDate()));
        }
    }

    @Transactional(readOnly = true)
    public PositionHistory getPositionById(Long id) {
        return positionHistoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Запись об отпуске по данному id: %s не найден", id)));
    }


    @Transactional(readOnly = true)
    public Page<PositionHistory> getAllByEmployeeId(Long employeeId, Pageable pageable) {
        return positionHistoryRepository.findByEmployee_Id(employeeId, pageable);
    }

    @Transactional
    public void deletePositionHistoryById(Long id) {
        if (positionHistoryRepository.findById(id).isPresent()) {
            positionHistoryRepository.delete(getPositionById(id));
        } else {
            throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
        }
    }
}