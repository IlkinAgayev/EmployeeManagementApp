package com.employee.management.model;

public record EmployeeResponse(int id, String name, String surname, String email, boolean status) {
}