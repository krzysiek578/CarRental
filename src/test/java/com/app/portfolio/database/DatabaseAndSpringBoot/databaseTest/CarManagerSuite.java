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
    @InjectMocks
    CarManager carManager;

    @Mock
    CarRepository carRepository;

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
        //When
        List<Car> cars = carManager.findAll();
        //Then
        Assertions.assertEquals(3, cars.size());
        verify(carRepository, times(1)).findAll();
    }


    @Test
    public void findByIdTest() {
        //Given
        given(carRepository.findById(2L)).willReturn(Optional.of(new Car("BMW", "E92", PetrolType.GASOLINE, true)));
        //When
        Optional<Car> car = carManager.findById(2L);
        //Then

        Assertions.assertEquals("E92", car.get().getModel());

        verify(carRepository, times(1)).findById(2L);
    }


    @Test
    public void saveTestWithoutID() {
        //Given
        Car car = new Car("BMW", "E92", PetrolType.GASOLINE, true);
        given(carRepository.save(car)).willReturn(car);
        //When
        Car carSaved = carManager.save(car);
        //Then
        Assertions.assertEquals("BMW", carSaved.getBrand());
        Assertions.assertEquals("E92", carSaved.getModel());
        Assertions.assertEquals(PetrolType.GASOLINE, carSaved.getPetrolType());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void saveTestWithID() {
        //Given
        Car car = new Car(2L ,"BMW", "E92", PetrolType.GASOLINE, true, null, null);
        given(carRepository.save(car)).willReturn(car);
        //When
        Car carSaved = carManager.save(car);
        //Then
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
        //When
        Optional<Car> changedCar = carManager.update(changeCar);
        //Then

        Assertions.assertEquals("E92 M3", changedCar.get().getModel());
        verify(carRepository, times(1)).save(changeCar);
    }

    @Test
    public void updateNotFoundObjetTest() {
        //Given
        given(carRepository.findById(2L)).willReturn(Optional.empty());
        Car changeCar = new Car(2L ,"BMW", "E92 M3", PetrolType.GASOLINE, true, null, null);
        given(carRepository.save(changeCar)).willReturn(changeCar);
        //When
        Optional<Car> changedCar = carManager.update(changeCar);
        //Then

        Assertions.assertFalse(changedCar.isPresent());
        verify(carRepository, never()).save(changeCar);
    }

    @Test
    public void deleteFindObjectTest() {
        //Given
        given(carRepository.existsById(2L)).willReturn(true);
        //When
        carManager.delete(2L);
        //Then
        verify(carRepository, times(1)).deleteById(2L);
    }

    @Test
    public void deleteNotFindObjectTest() {
        //Given
        given(carRepository.existsById(2L)).willReturn(false);
        //When
        carManager.delete(2L);
        //Then
        verify(carRepository, never()).deleteById(2L);
    }
}
