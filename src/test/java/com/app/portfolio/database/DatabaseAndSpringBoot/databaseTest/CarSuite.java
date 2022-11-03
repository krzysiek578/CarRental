package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolTyp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class CarSuite {

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void reset() {
        carRepository.deleteAll();
    }

    @Test
    public void testAddCar() {
            //Given
            Car car = new Car("Audi", "S3", PetrolTyp.GASOLINE, true);
            //When
            carRepository.save(car);
            Optional<Car> resultCar = carRepository.findById(car.getId());
            //Then
            Assertions.assertTrue(resultCar.isPresent());
            Assertions.assertEquals(car, resultCar.get());
    }

    @Test
    public void testSaveAll() {
        //Given
        List<Car> carList = List.of(
                new Car("Audi", "S3", PetrolTyp.GASOLINE, true),
                new Car("Audi", "S4", PetrolTyp.ELECTRIC, false),
                new Car("Audi", "S5", PetrolTyp.DIESEL, true),
                new Car("BMW", "M3", PetrolTyp.GASOLINE, false)
        );
        //When
        int size = carList.size();
        carRepository.saveAll(carList);
        List<Car> resultCar = carRepository.findAll();
        //Then
        Assertions.assertNotNull(resultCar);
        Assertions.assertEquals(resultCar.size(), size);
        Assertions.assertIterableEquals(carList, resultCar);
    }






}
