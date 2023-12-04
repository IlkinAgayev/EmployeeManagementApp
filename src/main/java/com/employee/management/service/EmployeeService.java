package com.employee.management.service;

import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;

import java.util.List;


public interface EmployeeService {
    EmployeeResponse saveEmployee(EmployeeRequest request);


    EmployeeResponse getEmployee(int id);

    List<EmployeeResponse> getAllEmployee();

    EmployeeResponse updateEmployee(int id, EmployeeRequest request);

    void deleteEmployeeById(int id);


}