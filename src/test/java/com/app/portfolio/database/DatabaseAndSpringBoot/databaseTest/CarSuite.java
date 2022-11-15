package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.DepartmentRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.Department;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
public class CarSuite {


    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    EntityManager entityManager;

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
        final Optional<Car> resultCarOptional = carRepository.findById(1L);
        //Then
        Assertions.assertTrue(resultCarOptional.isPresent());
        final Car resultCar = resultCarOptional.get();
        Assertions.assertEquals(car, resultCar);
        Assertions.assertEquals("Audi", resultCar.getBrand());
        Assertions.assertEquals("S3", resultCar.getModel());
        Assertions.assertEquals(PetrolType.GASOLINE, resultCar.getPetrolType());
        Assertions.assertTrue(resultCar.isEnabled());
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
        Assertions.assertEquals("M3", resultCar.get(3).getModel());
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
        final Car thirdTestCar = new Car("Volkswagen", "T3", PetrolType.DIESEL, true);

        //Or add all list with saveAll()
        carRepository.save(firstTestCar);
        carRepository.save(secondTestCar);
        carRepository.save(thirdTestCar);
        //When
        List<Car> carsSavedInBase = carRepository.findAll();
        carRepository.deleteAll();
        final List<Car> resultListCar = carRepository.findAll();
        //Then
        Assertions.assertEquals(3, carsSavedInBase.size());
        Assertions.assertEquals(0, resultListCar.size());
    }


    @Test
    public void findByIdTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        //When
        final Optional<Car> resultCarOptional = carRepository.findById(1L);
        //Then
        Assertions.assertTrue(resultCarOptional.isPresent());
        final Car resultCar = resultCarOptional.get();
        Assertions.assertEquals(car, resultCar);
        Assertions.assertEquals("Audi", resultCar.getBrand());
        Assertions.assertEquals("S3", resultCar.getModel());
//        Assertions.assertEquals(10, resultCar.getId());
        Assertions.assertEquals(PetrolType.ELECTRIC, resultCar.getPetrolType());
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
    @Transactional
    public void changeVariableTakeFromDatabase() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        //When
        carRepository.findById(1L).get().setBrand("Volvo");
        final Optional<Car> resultCarOptionalAfterChange = carRepository.findById(1L);

        //Then
        Assertions.assertTrue(resultCarOptionalAfterChange.isPresent());
        final Car resultCarAfterChange = resultCarOptionalAfterChange.get();
        Assertions.assertEquals("Volvo", resultCarAfterChange.getBrand());
    }


    @Test
    @Transactional
    public void departmentsAddToOneCarTest() {
        //Given
        Car car = new Car("Mercedes", "C63", PetrolType.ELECTRIC, false);
        carRepository.save(car);

        Department department = new Department("Test");
        Department secondDepartment = new Department("TestSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        car.addDepartment(department);
        car.addDepartment(secondDepartment);

        //When
        List<Department> departmentList = departmentRepository.findAll();

        //Then
        Assertions.assertEquals(2, departmentList.size());
        Assertions.assertEquals("TestSecond", departmentList.get(1).getName());
        Assertions.assertEquals(2, carRepository.findById(1L).get().getDepartmentsSet().size());
    }

    @Test
    @Transactional
    public void deleteDepartmentsFromCarTest() {
        //Given
        Car car = new Car("Mercedes", "C63", PetrolType.ELECTRIC, false);
        carRepository.save(car);
        final Car departmentsFromCarBeforeDelete = carRepository.findById(1L).get();

        Department department = new Department("Test");
        Department secondDepartment = new Department("TestSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        car.addDepartment(department);
        car.addDepartment(secondDepartment);

        //When
        final List<Department> departmentList = departmentRepository.findAll();
        Assertions.assertEquals(2, departmentList.size());

        final List<Car> carList = carRepository.findAll();

        Assertions.assertEquals("Mercedes",departmentsFromCarBeforeDelete.getBrand());


        car.removeDepartment(department);
        car.removeDepartment(secondDepartment);
        final List<Department> departmentsAfterRemoveFromCar = departmentRepository.findAll();
        final Set<Department> departmentsFromCar = car.getDepartmentsSet();
        //Then
        Assertions.assertEquals(2, departmentList.size());

        Assertions.assertEquals("TestSecond", departmentList.get(1).getName());
        Assertions.assertEquals(2, departmentsAfterRemoveFromCar.size());
        Assertions.assertEquals(0, departmentsFromCar.size());
        Assertions.assertEquals(1, carList.size());
    }

}
