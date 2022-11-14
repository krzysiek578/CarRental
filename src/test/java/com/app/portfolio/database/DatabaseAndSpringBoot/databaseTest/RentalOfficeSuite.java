package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.Department;
import com.app.portfolio.database.DatabaseAndSpringBoot.DepartmentRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.RentalOffice;
import com.app.portfolio.database.DatabaseAndSpringBoot.RentalOfficeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class RentalOfficeSuite {
    @Autowired
    RentalOfficeRepository rentalOfficeRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @AfterEach
    public void reset() {
        rentalOfficeRepository.deleteAll();
    }

    @Test
    @Transactional
    public void addSomeCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        Assertions.assertEquals("Audi", carRepository.findById(car.getId()).get().getBrand());
        Assertions.assertNull(carRepository.findById(car.getId()).get().getRentalOffice());

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);
        Assertions.assertEquals("Warsaw", rentalOfficeRepository.findById(rentalOffice.getId()).get().getCity());
        Assertions.assertEquals(0, rentalOfficeRepository.findById(rentalOffice.getId()).get().getCars().size());

        rentalOffice.addCar(car);
        Set<Car> resultCar = rentalOfficeRepository.getReferenceById(rentalOffice.getId()).getCars();
        //Then
        Assertions.assertEquals("S3", carRepository.findAll().get(0).getModel());
        Assertions.assertEquals(1, resultCar.size());
    }

    @Test
    @Transactional
    public void deleteRentalOfficeWithCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        Assertions.assertEquals("Audi", carRepository.findById(car.getId()).get().getBrand());
        Assertions.assertNull(carRepository.findById(car.getId()).get().getRentalOffice());


        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);
        Assertions.assertEquals("Warsaw", rentalOfficeRepository.findById(rentalOffice.getId()).get().getCity());
        Assertions.assertEquals(0, rentalOfficeRepository.findById(rentalOffice.getId()).get().getCars().size());

        //When
        rentalOffice.addCar(car);
        Assertions.assertTrue(rentalOfficeRepository.findById(rentalOffice.getId()).get().getCars().contains(car));
        Assertions.assertEquals(rentalOffice, carRepository.findById(car.getId()).get().getRentalOffice());

        rentalOfficeRepository.deleteAll();
        List<Car> carRepositoryAll = carRepository.findAll();
        //Then
        Assertions.assertEquals(0, rentalOfficeRepository.findAll().size());
        Assertions.assertEquals(0, carRepositoryAll.size());
    }

    @Test
    @Transactional
    public void saveNullOnCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondCar = new Car("Audi", "S2", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        carRepository.save(secondCar);

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);

        //When
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
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        Assertions.assertEquals("DepartmentSecond", departmentRepository.getReferenceById(secondDepartment.getId()).getName());
        Assertions.assertNull(departmentRepository.getReferenceById(secondDepartment.getId()).getRentalOffice());

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);

        Assertions.assertEquals("Office in Warsaw", rentalOfficeRepository.getReferenceById(rentalOffice.getId()).getName());
        Assertions.assertEquals(0, rentalOffice.getDepartments().size());

        //When
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        Set<Department> resultDepartments = rentalOfficeRepository.getReferenceById(rentalOffice.getId()).getDepartments();

        //Then
        Assertions.assertEquals(2, resultDepartments.size());
        Assertions.assertEquals("DepartmentSecond", secondDepartment.getName());
    }

    @Test
    @Transactional
    public void deleteRentalOfficeWithDepartmentsTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);
        Assertions.assertEquals("DepartmentSecond", departmentRepository.getReferenceById(secondDepartment.getId()).getName());
        Assertions.assertNull(departmentRepository.getReferenceById(secondDepartment.getId()).getRentalOffice());

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);
        Assertions.assertEquals("Office in Warsaw", rentalOfficeRepository.getReferenceById(rentalOffice.getId()).getName());
        Assertions.assertEquals(0, rentalOffice.getDepartments().size());

        //When
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        Assertions.assertEquals(2, rentalOffice.getDepartments().size());
        Assertions.assertEquals(rentalOffice, departmentRepository.findById(secondDepartment.getId()).get().getRentalOffice());
        Assertions.assertEquals("12-345", departmentRepository.findById(secondDepartment.getId()).get().getRentalOffice().getPostalCode());

        rentalOfficeRepository.deleteAll();
        final List<Department> departmentRepositoryAll = departmentRepository.findAll();


        //Then
        Assertions.assertEquals(0, departmentRepositoryAll.size());
        Assertions.assertEquals(0, rentalOfficeRepository.findAll().size());
    }

    @Test
    @Transactional
    public void deleteDepartmentFromRentalOfficeTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);

        //When
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        Assertions.assertEquals(2, rentalOffice.getDepartments().size());
        Assertions.assertEquals(rentalOffice, departmentRepository.findById(secondDepartment.getId()).get().getRentalOffice());
        Assertions.assertEquals("12-345", departmentRepository.findById(secondDepartment.getId()).get().getRentalOffice().getPostalCode());


        List<Department> departmentRepositoryAllBeforeDeleteDepartment = departmentRepository.findAll();
        Assertions.assertEquals(2, departmentRepositoryAllBeforeDeleteDepartment.size());

        rentalOffice.deleteDepartment(department);
        List<Department> departmentRepositoryAll = departmentRepository.findAll();
        //Then
        Assertions.assertFalse(departmentRepositoryAll.contains(department));
        Assertions.assertEquals(1, departmentRepositoryAll.size());

    }

}
