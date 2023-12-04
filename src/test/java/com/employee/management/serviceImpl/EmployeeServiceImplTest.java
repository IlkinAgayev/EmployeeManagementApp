package com.employee.management.serviceImpl;

import com.employee.management.entity.Department;
import com.employee.management.entity.Employee;
import com.employee.management.entity.Position;
import com.employee.management.exception.NotFoundException;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    void saveEmployeeTest() {
        // given
        int employeeId = 1;
        int departmentId = 1;
        int positionId = 1;

        var itDepartment = Department.builder().id(departmentId).name("IT Department").build();
        var positionItCoordinator = Position.builder().id(positionId).name("IT Coordinator").salary(60000).build();
        var employeeRequest = new EmployeeRequest("Asif", "Elekberov", "ae@gmail.com", true, itDepartment, positionItCoordinator);

        var employee = Employee.builder().id(employeeId).name("Asif").surname("Elekberov").email("ae@gmail.com").status(true).department(itDepartment).position(positionItCoordinator).build();


        // when
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeResponse employeeResponse = employeeService.saveEmployee(employeeRequest);

        //then
        assertNotNull(employeeResponse);


    }



    @Test
    void getEmployeeSuccessTest() {
        // given
        int employeeId = 1;
        int departmentId = 1;
        int positionId = 1;

        var itDepartment = Department.builder().id(departmentId).name("IT Department").build();
        var positionItCoordinator = Position.builder().id(positionId).name("IT Coordinator").salary(60000).build();

        var employee = Employee.builder().id(employeeId).name("Asif").surname("Elekberov").email("ae@gmail.com").status(true).department(itDepartment).position(positionItCoordinator).build();


        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        var employeeResponse = employeeService.getEmployee(employeeId);

        //then
        assertNotNull(employeeResponse);
        assertEquals(1, employeeResponse.id());
        assertEquals("Asif", employeeResponse.name());


    }





    @Test
    void getEmployeeErrorTest() {
        // when

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> employeeService.getEmployee(anyInt()));


    }


    @Test
    void getAllEmployeesSuccessTest() {
        // given
        int employeeId = 1;
        int employeeId2 = 2;
        int departmentId = 1;
        int positionId = 1;

        var itDepartment = Department.builder().id(departmentId).name("IT Department").build();
        var positionItCoordinator = Position.builder().id(positionId).name("IT Coordinator").salary(60000).build();

        var employee = Employee.builder().id(employeeId).name("Asif").surname("Elekberov").email("ae@gmail.com").status(true).department(itDepartment).position(positionItCoordinator).build();
        var employee2 = Employee.builder().id(employeeId2).name("Asif").surname("Elekberov").email("ae@gmail.com").status(true).department(itDepartment).position(positionItCoordinator).build();


        // when
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, employee2));
        var employeeResponse = employeeService.getAllEmployee();

        //then
        assertNotNull(employeeResponse);
        assertThat(employeeResponse.size()).isEqualTo(2);


    }



    @Test
    void getAllEmployeeErrorTest() {

        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        var responses = employeeService.getAllEmployee();

        assertNotNull(responses);
        assertThat(responses.size()).isEqualTo(0);

    }


    @Test
    void updateEmployeeSuccessTest() {
        // given
        int employeeId = 1;
        int departmentId = 1;
        int positionId = 1;

        var itDepartment = Department.builder().id(departmentId).name("IT Department").build();
        var positionItCoordinator = Position.builder().id(positionId).name("IT Coordinator").salary(60000).build();
        var employeeRequest = new EmployeeRequest("Qalib", "Elekberov", "ae@gmail.com", true, itDepartment, positionItCoordinator);

        var employee = Employee.builder().id(employeeId).name("Amal ").surname("Gadirov").email("ae@gmail.com").status(true).department(itDepartment).position(positionItCoordinator).build();


        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        var response = employeeService.updateEmployee(employeeId, employeeRequest);

        //then
        assertNotNull(response);
        assertEquals("Qalib", response.name());
        assertEquals("Elekberov", response.surname());

    }




    @Test
    void updateEmployeeErrorTest() {
        int employeeId = 1;

        var request = new EmployeeRequest("Asif", "Elekberov", "ae@gmail.com", true, null, null);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> employeeService.updateEmployee(employeeId, request));

    }


    @Test
    void deleteEmployeeSuccessTest() {
        // given
        int employeeId = 1;



        var employee = Employee.builder().id(employeeId).name("Amal ").surname("Gadirov").email("ae@gmail.com").status(true).department(null).position(null).build();


        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        willDoNothing().given(employeeRepository).delete(employee);
        employeeService.deleteEmployeeById(employeeId);

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).delete(employee);

    }

    @Test
    void deleteEmployeeErrorTest() {

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()-> employeeService.deleteEmployeeById(anyInt()));

        verify(employeeRepository, times(1)).findById(anyInt());
    }




}
