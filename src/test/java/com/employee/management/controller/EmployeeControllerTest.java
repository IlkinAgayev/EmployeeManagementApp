
package com.employee.management.controller;

import com.employee.management.entity.Department;
import com.employee.management.entity.Position;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    private static ObjectMapper objectMapper;


    @BeforeAll
    static void setObjectMapper() {
        objectMapper = new ObjectMapper();
    }


    @Test
    void saveEmployeeSuccessTest() throws Exception {
        // Given
        int employeeId = 1, departmentId = 1, positionId = 1;
        double positionSalary = 60000;
        String employeeName = "Rauf", departmentName = "IT Department", positionName = "IT Coordinator";
        String employeeSurname = "Elesgerov";
        String employeeEmail = "re@gmail.com";
        boolean employeeStatus = true;


        var itDepartment = Department.builder().id(departmentId).name(departmentName).build();
        var positionItCoordinator = Position.builder().id(positionId).name(positionName).salary(positionSalary).build();


        var request = new EmployeeRequest(employeeName, employeeSurname, employeeEmail, employeeStatus, itDepartment, positionItCoordinator);
        var response = new EmployeeResponse(employeeId, employeeName, employeeSurname, employeeEmail, employeeStatus);

        when(employeeService.saveEmployee(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employee-management/employees")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value(employeeName))
                .andExpect(jsonPath("$.surname").value(employeeSurname))
                .andExpect(jsonPath("$.email").value(employeeEmail))
                .andExpect(jsonPath("$.status").value(employeeStatus));

    }



    @Test
    void getAllEmployeesSuccessTest() throws Exception {
        int employeeId = 1;
        int employeeId2 = 2;
        var response = new EmployeeResponse(employeeId, "Rauf", "Elesgerov", "re@gmail.com", true);
        var response2 = new EmployeeResponse(employeeId2, "Asif", "Quliyev", "aq@gmail.com", true);


        when(employeeService.getAllEmployee()).thenReturn(Arrays.asList(response, response2));


        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee-management/employees/showAllEmployee")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));
    }



    @Test
    void getEmployeeSuccessTest() throws Exception {
        int employeeId = 1;
        String employeeName = "Rauf";
        String employeeSurname = "Elesgerov";
        String employeeEmail = "re@gmail.com";
        boolean employeeStatus = true;
        var response = new EmployeeResponse(employeeId, employeeName, employeeSurname, employeeEmail, employeeStatus);

        when(employeeService.getEmployee(employeeId)).thenReturn(response);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/employee-management/employees/{id}", employeeId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value(employeeName));
    }










    @Test
    void updateDepartmentSuccessTest() throws Exception {
        // Given
        int employeeId = 1;
        String employeeName = "Rauf";
        String employeeSurname = "Elesgerov";
        String employeeEmail = "re@gmail.com";
        boolean employeeStatus = true;

        var request = new EmployeeRequest(employeeName, employeeSurname, employeeEmail, employeeStatus, null, null);
        var response = new EmployeeResponse(employeeId, employeeName, employeeSurname, employeeEmail, employeeStatus);
        when(employeeService.updateEmployee(employeeId, request)).thenReturn(response);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/employee-management/employees/{id}", employeeId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value(employeeName))
                .andExpect(jsonPath("$.surname").value(employeeSurname))
                .andExpect(jsonPath("$.email").value(employeeEmail))
                .andExpect(jsonPath("$.status").value(employeeStatus));

    }



    @Test
    void deleteEmployeeSuccessTest() throws Exception {
        int employeeId = 5;

        willDoNothing().given(employeeService).deleteEmployeeById(employeeId);

        // When & Then

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/employee-management/employees/delete/{id}", employeeId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isNoContent())
                .andDo(print());
    }




}

