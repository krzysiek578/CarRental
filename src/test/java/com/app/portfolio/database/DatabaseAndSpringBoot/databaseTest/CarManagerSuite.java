package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarManager;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CarManagerSuite {
    @Mock
    CarRepository carRepository;
    
    @InjectMocks
    CarManager carManager;

    @Test
    public void findAllTest() {
        //Given
        given(carRepository.findAll()).willReturn(new ArrayList<>(
                        List.of(
                                new Car("Audi", "S3", PetrolType.ELECTRIC, false),
                                new Car("BMW", "E92", PetrolType.GASOLINE, true),
                                new Car("Porshe", "911 GT3", PetrolType.GASOLINE, true)
                        )
                )
        );
        //Then
        List<Car> cars = carManager.findAll();
        //When
        Assertions.assertEquals(3, cars.size());
        verify(carRepository, times(1)).findAll();
    }


    @Test
    public void findByIdTest() {
        //Given
        given(carRepository.findById(2L)).willReturn(Optional.of(new Car("BMW", "E92", PetrolType.GASOLINE, true)));
        //Then
        Optional<Car> car = carManager.findById(2L);
        //When
        car.ifPresent(a -> {
            Assertions.assertEquals("E92", a.getModel());
        });
        verify(carRepository, times(1)).findById(2L);
    }


    @Test
    public void saveTestWithoutID() {
        //Given
        Car car = new Car("BMW", "E92", PetrolType.GASOLINE, true);
        given(carRepository.save(car)).willReturn(car);
        //Then
        Car carSaved = carManager.save(car);
        //When
        Assertions.assertEquals(PetrolType.GASOLINE, carSaved.getPetrolType());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void saveTestWithID() {
        //Given
        Car car = new Car(2L ,"BMW", "E92", PetrolType.GASOLINE, true, null, null);
        given(carRepository.save(car)).willReturn(car);
        //Then
        Car carSaved = carManager.save(car);
        //When
        Assertions.assertEquals("BMW", carSaved.getBrand());
        Assertions.assertNull(carSaved.getId()); //null = niceMock
        verify(carRepository, times(1)).save(car);
    }


    @Test
    public void updateTest() {
        //Given
        Car car = new Car(2L ,"BMW", "E92", PetrolType.GASOLINE, true, null, null);
        given(carRepository.findById(2L)).willReturn(Optional.of(car));
        Car changeCar = new Car(2L ,"BMW", "E92 M3", PetrolType.GASOLINE, true, null, null);
        given(carRepository.save(changeCar)).willReturn(changeCar);
        //Then
        Optional<Car> changedCar = carManager.update(changeCar);
        //When
        changedCar.ifPresent(a -> {
            Assertions.assertEquals("E92 M3", a.getModel());
        });
        verify(carRepository, times(1)).save(changeCar);
    }

    @Test
    public void deleteFindObjectTest() {
        //Given
        given(carRepository.existsById(2L)).willReturn(true);
        //Then
        carManager.delete(2L);
        //
        verify(carRepository, times(1)).deleteById(2L);
        verify(carRepository).deleteById(2L);
    }

    @Test
    public void deleteNotFindObjectTest() {
        //Given
        given(carRepository.existsById(2L)).willReturn(false);
        //Then
        carManager.delete(2L);
        //When
        verify(carRepository, never()).deleteById(2L);
    }
}
