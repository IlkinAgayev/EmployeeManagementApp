package com.employee.management.mapper;


import com.employee.management.entity.Department;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DepartmentMapper {
    public static final DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    public abstract Department modelToEntity(DepartmentRequest request);

    public abstract void modelToEntity(@MappingTarget Department entity, DepartmentRequest request);

    public abstract DepartmentResponse entityToModel(Department department);

    public abstract List<DepartmentResponse> entityListToModelList(List<Department> departments);



}
