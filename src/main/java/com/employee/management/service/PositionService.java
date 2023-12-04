package com.employee.management.service;

import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;

import java.util.List;

public interface PositionService {
    PositionResponse savePosition(PositionRequest request);


    PositionResponse getPosition(int id);

    List<PositionResponse> getAllPosition();

    PositionResponse updatePosition(int id, PositionRequest request);

    void deletePositionById(int id);

}