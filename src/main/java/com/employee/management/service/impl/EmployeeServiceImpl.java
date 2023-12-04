package com.employee.management.service.impl;

import com.employee.management.entity.Employee;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.EmployeeMapper;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest request) {
        logger.info("ActionLog.saveEmployee.start request: {}", request);


        Employee employee = EmployeeMapper.INSTANCE.modelToEntity(request);
        EmployeeResponse response = EmployeeMapper.INSTANCE.entityToModel(employeeRepository.save(employee));

        logger.info("ActionLog.saveEmployee.end response: {}", response);
        return response;
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        logger.info("ActionLog.getAllEmployee.start");

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = EmployeeMapper.INSTANCE.entityListToModelList(employees);

        logger.info("ActionLog.getAllEmployee.end employees count: {}", employeeResponses.size());
        return employeeResponses;
    }

    @Override
    public EmployeeResponse getEmployee(int id) {
        logger.info("ActionLog.getEmployee.start id: {}", id);

        var employee = employeeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Employee NotFound id: " + id));

        EmployeeResponse employeeResponse = EmployeeMapper.INSTANCE.entityToModel(employee);

        logger.info("ActionLog.getEmployee.end id: {}", id);
        return employeeResponse;
    }

    @Override
    public EmployeeResponse updateEmployee(int id, EmployeeRequest request) {
        logger.info("ActionLog.updateEmployee.start id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee is NotFound id : " + id));

        EmployeeMapper.INSTANCE.modelToEntity(employee, request);
        Employee updatedEmployee = employeeRepository.save(employee);

        EmployeeResponse employeeResponse = EmployeeMapper.INSTANCE.entityToModel(updatedEmployee);

        logger.info("ActionLog.updateEmployee.end id: {}", id);
        return employeeResponse;
    }

    @Override
    public void deleteEmployeeById(int id) {
        logger.info("ActionLog.deleteEmployeeById.start id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee is NotFound id : " + id));

        employeeRepository.delete(employee);

        logger.info("ActionLog.deleteEmployeeById.end id: {}", id);
    }

}
