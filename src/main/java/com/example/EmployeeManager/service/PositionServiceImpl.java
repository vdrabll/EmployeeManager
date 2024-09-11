package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Position;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.PositionRepository;
import com.example.EmployeeManager.service.interfaces.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Transactional
    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Позиция по данному id: %s не найден", id)));
    }

    @Transactional
    public Position createPosition(Position position) {
       if (positionRepository.findByNameAndSalary(position.getName(), position.getSalary()).isEmpty()) {
           return positionRepository.save(position);
       } else {
           throw new RecordExistException(position.getName());
       }
    }

    @Transactional
    public void deletePositionById(Long id) {
        positionRepository.delete(getPositionById(id));
    }

    @Transactional
    public Position updatePosition(Long id, Position position) {
        Position positionById = getPositionById(id);
            positionById.setName(position.getName());
            positionById.setGrade(position.getGrade());
            positionById.setSalary(position.getSalary());
            return positionById;
    }
}
