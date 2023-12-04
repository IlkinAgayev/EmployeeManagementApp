package com.employee.management.model;

import com.employee.management.entity.Department;
import com.employee.management.entity.Position;

public record EmployeeRequest(String name, String surname, String email, boolean status, Department department, Position position) {
}