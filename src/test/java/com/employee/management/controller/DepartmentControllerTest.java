package com.employee.management.controller;

import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.service.impl.DepartmentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //salamTEch
    @MockBean
    private DepartmentServiceImpl departmentService;

    private static ObjectMapper objectMapper;


    @BeforeAll
    static void setObjectMapper() {
        objectMapper = new ObjectMapper();
    }


    @Test
    void saveDepartmentSuccessTest() throws Exception {
        // Given
        int departmentId = 1;
        String departmentName = "IT Department";
        var request = new DepartmentRequest(departmentName);
        var response = new DepartmentResponse(departmentId, departmentName);
        when(departmentService.saveDepartment(request)).thenReturn(response);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employee-management/departments")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value(departmentName));

    }


    @Test
    void getAllDepartmentSuccessTest() throws Exception {

        var responseOne = new DepartmentResponse(1, "IT Department");
        var responseTwo = new DepartmentResponse(2, "Software Department");
        when(departmentService.getAllDepartment()).thenReturn(Arrays.asList(responseOne, responseTwo));

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee-management/departments/showAllDepartment")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getDepartmentSuccessTest() throws Exception {
        int departmentId = 2;
        String departmentName = "Software Department";
        var response = new DepartmentResponse(departmentId, departmentName);
        when(departmentService.getDepartment(departmentId)).thenReturn(response);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee-management/departments/{id}", departmentId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value(departmentName));
    }






    @Test
    void updateDepartmentSuccessTest() throws Exception {
        // Given
        int departmentId = 1;
        String departmentName = "IT Department";
        var request = new DepartmentRequest(departmentName);
        var response = new DepartmentResponse(departmentId, departmentName);
        when(departmentService.updateDepartment(departmentId, request)).thenReturn(response);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/employee-management/departments/{id}", departmentId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value(departmentName));

    }


    @Test
    void deleteDepartmentSuccessTest() throws Exception {
        int departmentId = 20;

        willDoNothing().given(departmentService).deleteDepartmentById(departmentId);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employee-management/departments/delete/{id}", departmentId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}
