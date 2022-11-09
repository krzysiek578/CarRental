package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class CarSuite {


    private final CarRepository carRepository;


    @Autowired
    public CarSuite(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @AfterEach
    public void reset() {
        carRepository.deleteAll();
    }

    @Test
    public void saveCarTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.GASOLINE, true);
        //When
        carRepository.save(car);
        final Optional<Car> resultCar = carRepository.findById(car.getId());
        //Then
        Assertions.assertTrue(resultCar.isPresent());
        Assertions.assertEquals(car, resultCar.get());
    }

    @Test
    public void testSaveAll() {
        //Given
        final List<Car> carList = List.of(
                new Car("Audi", "S3", PetrolType.GASOLINE, true),
                new Car("Audi", "S4", PetrolType.ELECTRIC, false),
                new Car("Audi", "S5", PetrolType.DIESEL, true),
                new Car("BMW", "M3", PetrolType.GASOLINE, false)
        );
        //When
        int size = carList.size();
        carRepository.saveAll(carList);
        final List<Car> resultCar = carRepository.findAll();
        //Then
        Assertions.assertNotNull(resultCar);
        Assertions.assertEquals(resultCar.size(), size);
        Assertions.assertIterableEquals(carList, resultCar);
    }

    @Test
    public void deleteCarTest() {
        //Given
        final Car testCar = new Car("Audi", "S3", PetrolType.GASOLINE, true);
        final Car secondTestCar = new Car("Audi", "S3", PetrolType.GASOLINE, true);
        carRepository.save(testCar);
        carRepository.save(secondTestCar);
        //When
        carRepository.delete(testCar);
        List<Car> carRepositoryAll = carRepository.findAll();
        //Then
        Assertions.assertEquals(1, carRepositoryAll.size());
        Assertions.assertEquals(secondTestCar, carRepositoryAll.get(0));
    }

    @Test
    public void deleteAllTest() {
        //Given
        final Car firstTestCar = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondTestCar = new Car("BMW", "M3", PetrolType.GASOLINE, true);
        final Car thirdTestCar = new Car("Volskwagen", "T3", PetrolType.DIESEL, true);

        //Or add all list with saveAll()
        carRepository.save(firstTestCar);
        carRepository.save(secondTestCar);
        carRepository.save(thirdTestCar);
        //When
        carRepository.deleteAll();
        final List<Car> resultListCar = carRepository.findAll();
        //Then
        Assertions.assertEquals(0, resultListCar.size());
    }



    @Test
    public void findByIdTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        //When
        final Optional<Car> resultCarOptional = carRepository.findById(car.getId());
        //Then
        Assertions.assertTrue(resultCarOptional.isPresent());
        final Car resultCar = resultCarOptional.get();
        Assertions.assertEquals(car, resultCar);
        Assertions.assertEquals(car.getBrand(), resultCar.getBrand());
        Assertions.assertEquals(car.getModel(), resultCar.getModel());
        Assertions.assertEquals(car.getId(), resultCar.getId());
        Assertions.assertEquals(car.getPetrolType(), resultCar.getPetrolType());
    }

    @Test
    public void findAllTest() {
        //Given
        final Car firstTestCar = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondTestCar = new Car("BMW", "M3", PetrolType.GASOLINE, true);
        final Car thirdTestCar = new Car("Volskwagen", "T3", PetrolType.DIESEL, true);
        final List<Car> testListCars = new ArrayList<>(
                List.of(
                        firstTestCar,
                        secondTestCar,
                        thirdTestCar
                )
        );
        //Or add all list with saveAll()
        carRepository.save(firstTestCar);
        carRepository.save(secondTestCar);
        carRepository.save(thirdTestCar);
        //When
        final List<Car> resultListCar = carRepository.findAll();
        //Then
        Assertions.assertEquals(3, resultListCar.size());
        Assertions.assertIterableEquals(testListCars, resultListCar); // What does do?
        Assertions.assertEquals(testListCars, resultListCar); // What does do?
    }

    @Test
    public void changeVariableTakeFromDatabase() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        //When
        carRepository.findById(car.getId()).get().setBrand("Volvo");
        final Optional<Car> resultCarOptionalAfterChange = carRepository.findById(car.getId());

        //Then
        Assertions.assertTrue(resultCarOptionalAfterChange.isPresent());
        final Car resultCarAfterChange = resultCarOptionalAfterChange.get();
        Assertions.assertEquals("Volvo", resultCarAfterChange.getBrand());
    }


}
