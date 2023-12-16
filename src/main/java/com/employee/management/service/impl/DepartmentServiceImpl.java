package com.employee.management.service.impl;

import com.employee.management.entity.Department;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.DepartmentMapper;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse saveDepartment(DepartmentRequest request) {
        logger.info("ActionLog.saveDepartment.start request: {}", request);

        Department department = DepartmentMapper.INSTANCE.modelToEntity(request);
        DepartmentResponse response = DepartmentMapper.INSTANCE.entityToModel(departmentRepository.save(department));

        logger.info("ActionLog.saveDepartment.end response: {}", response);
        return response;


    }


    @Override
    public List<DepartmentResponse> getAllDepartment() {
        logger.info("ActionLog.getAllDepartment.start");

        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> departmentResponses = DepartmentMapper.INSTANCE.entityListToModelList(departments);

        logger.info("ActionLog.getAllDepartment.end departments count: {}", departmentResponses.size());
        return departmentResponses;

    }

    @Override
    public DepartmentResponse getDepartment(int id) {
        logger.info("ActionLog.getDepartment.start id: {}", id);

        var department = departmentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Department is NotFound id :" + id));

        DepartmentResponse response = DepartmentMapper.INSTANCE.entityToModel(department);

        logger.info("ActionLog.getDepartment.end id: {}", id);
        return response;

    }


    @Override
    public DepartmentResponse updateDepartment(int id, DepartmentRequest request) {
        logger.info("ActionLog.updateDepartment.start id: {}", id);
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is NotFound id : " + id));

        DepartmentMapper.INSTANCE.modelToEntity(department, request);
        var updatedDepartment = departmentRepository.save(department);

        DepartmentResponse response = DepartmentMapper.INSTANCE.entityToModel(updatedDepartment);

        logger.info("ActionLog.updateDepartment.end id: {}", id);
        return response;

    }

    @Override
    public void deleteDepartmentById(int id) {
        logger.info("ActionLog.deleteDepartmentById.start id: {}", id);

        var department = departmentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Department is NotFound id : " + id));

        departmentRepository.delete(department);

        logger.info("ActionLog.deleteDepartmentById.end id: {}", id);
    }



}
