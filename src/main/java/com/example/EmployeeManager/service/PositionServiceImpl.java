package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.PositionRepository;
import com.example.EmployeeManager.service.interfaces.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Transactional
    public Position createPosition(Position position) {
        if (positionRepository.findByNameAndSalary(position.getName(), position.getSalary()).isEmpty()) {
            return positionRepository.save(position);
        } else {
            log.error("Record with {} already exists", position.getId());
            throw new RecordExistException(position.getName());
        }
    }

    @Transactional(readOnly = true)
    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Позиция по данному id: %s не найден", id)));
    }

    @Transactional
    public Position updatePosition(Long id, Position position) {
        Position positionById = getPositionById(id);
        positionById.setName(position.getName());
        positionById.setGrade(position.getGrade());
        positionById.setSalary(position.getSalary());
        return positionById;
    }

    @Transactional
    public void deletePositionById(Long id) {
        if (positionRepository.findById(id).isPresent()) {
            positionRepository.delete(getPositionById(id));
        } else {
            throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
        }
    }
}
