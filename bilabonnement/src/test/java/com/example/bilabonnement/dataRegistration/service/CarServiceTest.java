package com.example.bilabonnement.dataRegistration.service;

import com.example.bilabonnement.dataRegistration.model.view.CarView;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/* Tester CarService klassens metoder:
 */

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepo carRepo;

    @InjectMocks
    CarService carService;

    @Test
    void listAllCars_returnsListFromRepo(){

        //Arrange
        CarView c1 = new CarView();
        CarView c2 = new CarView();
        when(carRepo.fetchAllCars()).thenReturn(List.of(c1, c2));

        //Act:
        List<CarView> result = carService.listAllCars();

        //Assert:

        assertEquals(2, result.size());
        verify(carRepo).fetchAllCars();

    }

    @Test
    void findCarById_returnsCarFromRepo(){
        //Arrange:
        CarView car = new CarView();
        car.setVehicleId(10);
        when(carRepo.fetchCarById(10)).thenReturn(car);

        //Act:
        CarView result = carService.findCarById(10);

        //Assert:
        assertNotNull(result);
        assertEquals(10, result.getVehicleId());
        verify(carRepo).fetchCarById(10);
    }

    @Test
    void listAllCarsByStatus_returnsFilteredListFromRepo(){
        //Arrange:

        CarView c1 = new CarView();
        when(carRepo.fetchCarsByStatus("READY_FOR_RENT")).thenReturn(List.of(c1));

        //Act:
        List<CarView> result = carService.listAllCarsByStatus("READY_FOR_RENT");

        //Assert:
        assertEquals(1, result.size());
        verify(carRepo).fetchCarsByStatus("READY_FOR_RENT");
    }

    @Test
    void changeCarStatus_callsRepoCorrectly() {

        //Arrange:
        when(carRepo.insertStatusHistory(5, "RENTED")).thenReturn(true);

        //Act:
        boolean result = carService.changeCarStatus(5, "RENTED");

        //Assert:
        assertTrue(result);
        verify(carRepo).insertStatusHistory(5, "RENTED");
    }



}