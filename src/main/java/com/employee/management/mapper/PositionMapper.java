package com.employee.management.mapper;


import com.employee.management.entity.Position;
import com.employee.management.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PositionMapper {
    public static final PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);


    public abstract Position modelToEntity(PositionRequest request);

    public abstract void modelToEntity(@MappingTarget Position entity, PositionRequest request);

    public abstract PositionResponse entityToModel(Position position);

    public abstract List<PositionResponse> entityListToModelList(List<Position> positions);


}
