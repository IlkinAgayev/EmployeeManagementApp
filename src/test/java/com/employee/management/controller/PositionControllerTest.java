package com.employee.management.controller;

import com.employee.management.entity.Department;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.service.impl.PositionServiceImpl;
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
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionServiceImpl positionService;

    private static ObjectMapper objectMapper;


    @BeforeAll
    static void setObjectMapper() {
        objectMapper = new ObjectMapper();
    }


    @Test
    void savePositionSuccessTest() throws Exception {

        int departmentId = 1;
        int positionId = 1;
        double salary = 60000;
        String positionName = "IT Coordinator";
        String departmentName = "IT Department";

        var itDepartment = Department.builder().id(departmentId).name(departmentName).build();

        var request = new PositionRequest(positionName,salary, itDepartment);
        var response = new PositionResponse(positionId, positionName);
        when(positionService.savePosition(request)).thenReturn(response);



        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employee-management/positions")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(positionId))
                .andExpect(jsonPath("$.name").value(positionName));

    }


    @Test
    void getAllPositionSuccessTest() throws Exception {

        var responseOne = new PositionResponse(1, "IT Coordinator");
        var responseTwo = new PositionResponse(2, "Network administrator.");
        when(positionService.getAllPosition()).thenReturn(Arrays.asList(responseOne, responseTwo));



        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee-management/positions/showAllPosition")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));
    }




    @Test
    void getPositionSuccessTest() throws Exception {
        int positionId = 2;
        String positionName = "IT Coordinator";
        var response = new PositionResponse(positionId, positionName);
        when(positionService.getPosition(positionId)).thenReturn(response);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee-management/positions/{id}", positionId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(positionId))
                .andExpect(jsonPath("$.name").value(positionName));
    }




    @Test
    void updatePositionSuccessTest() throws Exception {

        int departmentId = 1;
        int positionId = 1;
        double salary = 60000;
        String positionName = "IT Coordinator";
        String departmentName = "IT Department";

        var itDepartment = Department.builder().id(departmentId).name(departmentName).build();

        var request = new PositionRequest(positionName, salary, itDepartment);
        var response = new PositionResponse(positionId, positionName);
        when(positionService.updatePosition(positionId, request)).thenReturn(response);



        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/employee-management/positions/{id}", positionId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(positionId))
                .andExpect(jsonPath("$.name").value(positionName));

    }



    @Test
    void deleteDepartmentSuccessTest() throws Exception {
        int positionId = 1;

        willDoNothing().given(positionService).deletePositionById(positionId);


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employee-management/positions/delete/{id}", positionId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isNoContent())
                .andDo(print());
    }



}
