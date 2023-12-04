package com.employee.management.mapper;

import com.employee.management.entity.Employee;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class EmployeeMapper {
    public static final EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);


    public abstract Employee modelToEntity(EmployeeRequest request);

    public abstract void modelToEntity(@MappingTarget Employee entity, EmployeeRequest request);

    public abstract EmployeeResponse entityToModel(Employee employee);

    public abstract List<EmployeeResponse> entityListToModelList(List<Employee> employees);




}
