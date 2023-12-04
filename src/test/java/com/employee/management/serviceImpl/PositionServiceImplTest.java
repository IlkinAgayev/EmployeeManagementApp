

package com.employee.management.serviceImpl;

import com.employee.management.entity.Department;
import com.employee.management.entity.Position;
import com.employee.management.exception.NotFoundException;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.repository.PositionRepository;
import com.employee.management.service.impl.PositionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionServiceImpl positionService;

    @Test
    void savePositionTest() {

        int positionId = 1;
        int departmentId = 1;

        String name = "IT Coordinator";
        double salary = 60000;
        Department department = Department.builder().id(departmentId).name("IT Department").build();

        var positionRequest = new PositionRequest(name, salary, department);

        var position = Position.builder().id(positionId).name(name).salary(salary).department(department).build();


        when(positionRepository.save(any(Position.class))).thenReturn(position);
        var positionResponse = positionService.savePosition(positionRequest);


        assertNotNull(positionResponse);
        assertEquals(1, positionResponse.id());
        assertEquals("IT Coordinator", positionResponse.name());
    }

    @Test
    public void getPositionSuccessTest() {

        int positionId = 1;
        String name = "IT Coordinator";
        double salary = 60000;

        var position = Position.builder().id(positionId).name(name).salary(salary).build();


        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        var response = positionService.getPosition(positionId);



        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("IT Coordinator", response.name());

    }


    @Test
    public void getPositionErrorTest() {

        int positionId = 1;


        when(positionRepository.findById(positionId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> positionService.getPosition(positionId));

    }


    @Test
    public void getAllPositionSuccessTest() {
        Position position1 = Position.builder().id(1).name("IT Coordinator").build();
        Position position2 = Position.builder().id(2).name("Network administrator.").build();

        when(positionRepository.findAll()).thenReturn(Arrays.asList(position1, position2));
        var response = positionService.getAllPosition();

        assertNotNull(response);
        assertThat(response.size()).isEqualTo(2);

    }


    @Test
    public void getAllPositionErrorTest() {

        when(positionRepository.findAll()).thenReturn(new ArrayList<>());
        var response = positionService.getAllPosition();

        assertThat(response.size()).isEqualTo(0);
    }




    @Test
    public void updatePositionSuccessTest() {


        int positionId = 1;
        int departmentId = 1;
        String name = "IT Coordinator";
        double salary = 60000;


        Department department = Department.builder().id(departmentId).name("It Department").build();

        PositionRequest request = new PositionRequest(name, salary, department);
        var position = Position.builder().id(positionId).name("Network administrator.").build();

        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        when(positionRepository.save(any(Position.class))).thenReturn(position);
        PositionResponse response = positionService.updatePosition(positionId, request);


        assertNotNull(response);
        assertEquals("IT Coordinator", response.name());
    }

    @Test
    public void updatePositionErrorTest() {

        int positionId = 1;
        PositionRequest request = new PositionRequest("IT Coordinator", 60000, null);

        when(positionRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()-> positionService.updatePosition(positionId,request));
    }



    @Test
    public void deletePositionSuccessTest() {

        int positionId = 1;
        var position = Position.builder().id(positionId).name("IT Coordinator").salary(60000).build();

        given(positionRepository.findById(positionId)).willReturn(Optional.of(position));
        willDoNothing().given(positionRepository).delete(position);

        positionService.deletePositionById(positionId);


        verify(positionRepository, times(1)).findById(positionId);
        verify(positionRepository, times(1)).delete(position);



    }


    @Test
    public void deletePositionErrorTest() {
        given(positionRepository.findById(anyInt())).willReturn(Optional.empty());


        assertThrows(NotFoundException.class, ()-> positionService.deletePositionById(anyInt()));
    }


}


