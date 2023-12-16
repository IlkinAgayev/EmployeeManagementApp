package com.employee.management.service.impl;

import com.employee.management.entity.Position;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.PositionMapper;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.repository.PositionRepository;
import com.employee.management.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    @Override
    public PositionResponse savePosition(PositionRequest request) {
        logger.info("ActionLog.savePosition.start request: {}", request);

        Position position = PositionMapper.INSTANCE.modelToEntity(request);
        PositionResponse response = PositionMapper.INSTANCE.entityToModel(positionRepository.save(position));

        logger.info("ActionLog.savePosition.end response: {}", response);
        return response;
    }


    @Override
    public List<PositionResponse> getAllPosition() {
        logger.info("ActionLog.getAllPosition.start");

        List<Position> positions = positionRepository.findAll();
        List<PositionResponse> positionResponses = PositionMapper.INSTANCE.entityListToModelList(positions);

        logger.info("ActionLog.getAllPosition.end positions count: {}", positionResponses.size());
        return positionResponses;
    }


    @Override
    public PositionResponse getPosition(int id) {
        logger.info("ActionLog.getPosition.start id: {}", id);

        var position = positionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Position is NotFound"));

        PositionResponse positionResponse = PositionMapper.INSTANCE.entityToModel(position);


        logger.info("ActionLog.getPosition.end id: {}", id);
        return positionResponse;
    }

    @Override
    public PositionResponse updatePosition(int id, PositionRequest request) {
        logger.info("ActionLog.updatePosition.start id: {}", id);

        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position is NotFound is id : " + id));

        PositionMapper.INSTANCE.modelToEntity(position, request);
        Position updatePosition = positionRepository.save(position);

        PositionResponse response = PositionMapper.INSTANCE.entityToModel(updatePosition);

        logger.info("ActionLog.updatePosition.end id: {}", id);
        return response;
    }

    @Override
    public void deletePositionById(int id) {
        logger.info("ActionLog.deletePositionById.start id: {}", id);

        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position is NotFound id : " + id));

        positionRepository.delete(position);

        logger.info("ActionLog.deletePositionById.end id: {}", id);
    }
}