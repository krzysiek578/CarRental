package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
public class RentalOfficeSuite {
    @Autowired
    RentalOfficeRepository rentalOfficeRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager entityManager;

    @AfterEach
    public void reset() {
        rentalOfficeRepository.deleteAll();
    }

    @Test
    @Transactional
    public void addSomeCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondCar = new Car("Audi", "S2", PetrolType.ELECTRIC, true);
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        entityManager.persist(rentalOffice);
        rentalOffice.addCar(car);
        rentalOffice.addCar(secondCar);
        List<Car> resultCar = rentalOfficeRepository.getReferenceById(rentalOffice.getId()).getCars();
        //Then
        Assertions.assertEquals(2, resultCar.size());
        Assertions.assertEquals(car, resultCar.get(0));
        Assertions.assertEquals(secondCar, resultCar.get(1));
        Assertions.assertEquals("S2", secondCar.getModel());
    }

    @Test
    @Transactional
    public void deleteRentalOfficeWithCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondCar = new Car("Audi", "S2", PetrolType.ELECTRIC, true);
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        entityManager.persist(rentalOffice);
        rentalOffice.addCar(car);
        rentalOffice.addCar(secondCar);
        rentalOfficeRepository.deleteAll();
        List<Car> carRepositoryAll = carRepository.findAll();
        //Then
        Assertions.assertEquals(0, carRepositoryAll.size());
    }

    @Test
    @Transactional
    public void saveNullOnCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondCar = new Car("Audi", "S2", PetrolType.ELECTRIC, true);
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        entityManager.persist(rentalOffice);
        rentalOffice.addCar(car);
        rentalOffice.addCar(secondCar);
        List<Car> carRepositoryAllBeforeDeleteCar = carRepository.findAll();
        rentalOffice.deleteCar(car);
        List<Car> carRepositoryAll = carRepository.findAll();
        //Then
        Assertions.assertEquals(2, carRepositoryAllBeforeDeleteCar.size());
        Assertions.assertFalse(carRepositoryAll.contains(car));
        Assertions.assertEquals(1, carRepositoryAll.size());
    }



    @Test
    @Transactional
    public void addSomeDepartmentsTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        entityManager.persist(rentalOffice);
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        List<Department> resultDepartments = rentalOfficeRepository.getReferenceById(rentalOffice.getId()).getDepartments();
        //Then
        Assertions.assertEquals(2, resultDepartments.size());
        Assertions.assertEquals(department, resultDepartments.get(0));
        Assertions.assertEquals(secondDepartment, resultDepartments.get(1));
        Assertions.assertEquals("DepartmentSecond", secondDepartment.getName());
    }

    @Test
    @Transactional
    public void deleteRentalOfficeWithDepartmentsTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        entityManager.persist(rentalOffice);
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        rentalOfficeRepository.deleteAll();
        List<Department> departmentRepositoryAll = departmentRepository.findAll();
        //Then
        Assertions.assertEquals(0, departmentRepositoryAll.size());
    }

    @Test
    @Transactional
    public void saveNullOnDepartamentsTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        entityManager.persist(rentalOffice);
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        List<Department> departmentRepositoryAllBeforeDeleteCar = departmentRepository.findAll();
        rentalOffice.deleteDepartment(department);
        List<Department> departmentRepositoryAll = departmentRepository.findAll();
        //Then
        Assertions.assertEquals(2, departmentRepositoryAllBeforeDeleteCar.size());
        Assertions.assertFalse(departmentRepositoryAll.contains(department));
        Assertions.assertEquals(1, departmentRepositoryAll.size());
    }

}
