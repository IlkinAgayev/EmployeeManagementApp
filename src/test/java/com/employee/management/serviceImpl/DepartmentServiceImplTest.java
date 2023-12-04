package com.employee.management.serviceImpl;

import com.employee.management.entity.Department;
import com.employee.management.exception.NotFoundException;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;


    @DisplayName("Returning the department according to the given ID")
    @Test
    public void getDepartmentSuccessTest() {
        // given
        int departmentId = 1;
        Department department = Department.builder().id(departmentId).name("It Department").build();
        given(departmentRepository.findById(departmentId)).willReturn(Optional.of(department));

        // when
        DepartmentResponse departmentResponse = departmentService.getDepartment(departmentId);

        // when & then

        assertThat(departmentResponse).isNotNull();
        assertEquals(1, departmentResponse.id());
        assertEquals("It Department", departmentResponse.name());

    }

    @DisplayName("Returning the department error according to the given ID")
    @Test
    public void getDepartmentErrorTest() {
        // given
        given(departmentRepository.findById(anyInt())).willReturn(Optional.empty());

        // when

        // when & then
        assertThrows(NotFoundException.class, () -> departmentService.getDepartment(anyInt()));
    }





    @DisplayName("Saving the department according to the specified name")
    @Test
    public void saveDepartmentSuccessTest() {

        int id = 1;
        DepartmentRequest request = new DepartmentRequest("It Department");
        Department department = Department.builder().id(id).name("It Department").build();


        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentResponse departmentResponse = departmentService.saveDepartment(request);


        assertThat(departmentResponse).isNotNull();

    }


    @DisplayName("JUnit test for getAllDepartments method ")
    @Test
    public void getAllDepartmentSuccessTest() {
        // given

        Department department1 = Department.builder().id(1).name("It Department").build();
        Department department2 = Department.builder().id(2).name("Software Department").build();


        // when
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));
        List<DepartmentResponse> allDepartment = departmentService.getAllDepartment();

        // then

        assertThat(allDepartment.size()).isEqualTo(2);

    }






    @DisplayName("JUnit test for deleteDepartment method")
    @Test
    public void deleteDepartmentSuccessTest() {
        // given
        int id = 1;
        Department department = Department.builder().id(id).name("It Department").build();

        given(departmentRepository.findById(id)).willReturn(Optional.of(department));
        willDoNothing().given(departmentRepository).delete(department);

        // when
        departmentService.deleteDepartmentById(id);

        // then

        verify(departmentRepository, times(1)).findById(id);
        verify(departmentRepository, times(1)).delete(department);



    }



    @DisplayName("Returning the delete department error according to the given ID")
    @Test
    public void deleteDepartmentErrorTest() {
        // given
        given(departmentRepository.findById(anyInt())).willReturn(Optional.empty());

        // when

        // when & then
        assertThrows(NotFoundException.class, ()-> departmentService.deleteDepartmentById(anyInt()));
    }


    @DisplayName("Returning the new department according to the given Department name")
    @Test
    public void updateDepartmentSuccessTest() {
        // given
        int id = 1;
        DepartmentRequest request = new DepartmentRequest("It Department");
        Department department = Department.builder().id(id).name("It Department").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));
        department.setName(request.name());

        // when
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentResponse departmentResponse = departmentService.updateDepartment(id, request);

        // then

        assertThat(departmentResponse).isNotNull();





    }



    @DisplayName("Returning the delete department error according to the given ID")
    @Test
    public void updateDepartmentErrorTest() {
        // given
        int id = 1;
        DepartmentRequest request = new DepartmentRequest("Software Department");
        given(departmentRepository.findById(anyInt())).willReturn(Optional.empty());

        // when

        // when & then
        assertThrows(NotFoundException.class, ()-> departmentService.updateDepartment(id,request));
    }



}
