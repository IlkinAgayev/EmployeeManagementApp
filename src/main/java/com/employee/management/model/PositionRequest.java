package com.employee.management.model;


import com.employee.management.entity.Department;

public record PositionRequest(String name, double salary, Department department) {
}