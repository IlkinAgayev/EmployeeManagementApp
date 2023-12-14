package com.employee.management.controller;

import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${root.url}/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentResponse save(@RequestBody DepartmentRequest request) {
        return departmentService.saveDepartment(request);
    }

    @GetMapping("/showAllDepartment")
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartment();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable int id) {
        return departmentService.getDepartment(id);
    }

    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(@PathVariable int id, @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}