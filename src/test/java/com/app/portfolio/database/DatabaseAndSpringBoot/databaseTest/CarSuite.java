package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.deprtment.DepartmentRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.deprtment.Department;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
public class CarSuite {


    @Autowired
    private CarRepository carRepository;


    @Autowired
    private DepartmentRepository departmentRepository;


    @AfterEach
    public void clearDatabase() {
        departmentRepository.deleteAll();
        carRepository.deleteAll();
    }
    @BeforeEach
    public void clearDatabaseBefore() {
        departmentRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
        Assertions.assertEquals(PetrolType.ELECTRIC, resultCar.getPetrolType());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void departmentsAddToOneCarTest() {
        //Given
        Car car = new Car("Mercedes", "C63", PetrolType.ELECTRIC, false);
        carRepository.save(car);

        Department department = new Department("Test");
        Department secondDepartment = new Department("TestSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        Set<Department> departmentList = new HashSet<>(Set.of(department, secondDepartment));
        car.setDepartments(departmentList);


        //When
        List<Department> departmentListFromDatabase = departmentRepository.findAll();

        //Then
        Assertions.assertEquals(2, departmentList.size());
        Assertions.assertEquals("TestSecond", departmentListFromDatabase.get(1).getName());
        Assertions.assertEquals(2, carRepository.findById(1L).get().getDepartments().size());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteDepartmentsFromCarTest() {
        //Given
        Car car = new Car("Mercedes", "C63", PetrolType.ELECTRIC, false);
        carRepository.save(car);
        final Car carBeforeAddDepartments = carRepository.findById(1L).get();
        Assertions.assertEquals("Mercedes",carBeforeAddDepartments.getBrand());

        Department department = new Department("Test");
        Department secondDepartment = new Department("TestSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        Set<Department> departmentList = new HashSet<>(Set.of(department, secondDepartment));
        car.setDepartments(departmentList);
        Assertions.assertEquals(2, departmentList.size());

        //When
        final List<Department> departmentListFromDatabase = departmentRepository.findAll();
        Assertions.assertEquals(2, departmentListFromDatabase.size());

        final List<Car> carList = carRepository.findAll();




        car.getDepartments().remove(department);
        car.getDepartments().remove(secondDepartment);
        final List<Department> departmentsAfterRemoveFromCar = departmentRepository.findAll();
        final Set<Department> departmentsFromCar = car.getDepartments();
        //Then


        Assertions.assertEquals("TestSecond", departmentRepository.findById(2L).get().getName());
        Assertions.assertFalse(department.getCars().contains(car));
        Assertions.assertFalse(secondDepartment.getCars().contains(car));
        Assertions.assertEquals(2, departmentsAfterRemoveFromCar.size());
        Assertions.assertEquals(0, departmentsFromCar.size());
        Assertions.assertEquals(1, carList.size());
    }

}
