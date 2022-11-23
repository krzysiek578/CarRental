package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.Department;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOffice;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOfficeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addSomeCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        Assertions.assertEquals("Audi", carRepository.findById(1L).get().getBrand());
        Assertions.assertNull(carRepository.findById(1L).get().getRentalOffice());

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);
        Assertions.assertEquals("Warsaw", rentalOfficeRepository.findById(1L).get().getCity());
        Assertions.assertEquals(0, rentalOfficeRepository.findById(1L).get().getCars().size());

        Set<Car> carsSetBefore = new HashSet<>(
                Set.of(car)
        );
        rentalOffice.setCars(carsSetBefore);

        Set<Car> resultCar = rentalOfficeRepository.getReferenceById(1L).getCars();
        //Then
        Assertions.assertEquals("S3", carRepository.findAll().get(0).getModel());
        Assertions.assertEquals(1, resultCar.size());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteRentalOfficeWithCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        Assertions.assertEquals("Audi", carRepository.findById(1L).get().getBrand());
        Assertions.assertNull(carRepository.findById(1L).get().getRentalOffice());


        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);
        Assertions.assertEquals("Warsaw", rentalOfficeRepository.findById(1L).get().getCity());
        Assertions.assertEquals(0, rentalOfficeRepository.findById(1L).get().getCars().size());

        //When
        rentalOffice.addCar(car);

        Assertions.assertTrue(rentalOfficeRepository.findById(1L).get().getCars().contains(car));
        Assertions.assertEquals(rentalOffice, carRepository.findById(1L).get().getRentalOffice());

        rentalOfficeRepository.deleteAll();
        final List<Car> carRepositoryAll = carRepository.findAll();
        //Then
        Assertions.assertEquals(0, rentalOfficeRepository.findAll().size());
        Assertions.assertEquals(0, carRepositoryAll.size());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveNullOnCarsTest() {
        //Given
        final Car car = new Car("Audi", "S3", PetrolType.ELECTRIC, true);
        final Car secondCar = new Car("Audi", "S2", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        carRepository.save(secondCar);

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);

        //When
        Set<Car> carsSetBefore = new HashSet<>();
        carsSetBefore.add(car);
        carsSetBefore.add(secondCar);

        rentalOffice.setCars(carsSetBefore);
        final List<Car> carRepositoryAllBeforeDeleteCar = carRepository.findAll();

        rentalOffice.getCars().remove(car);
        final List<Car> carRepositoryAll = carRepository.findAll();
        //Then
        Assertions.assertEquals(2, carRepositoryAllBeforeDeleteCar.size());
        Assertions.assertFalse(carRepositoryAll.contains(car));
        Assertions.assertEquals(1, carRepositoryAll.size());
    }


    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addSomeDepartmentsTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        Assertions.assertEquals("DepartmentSecond", departmentRepository.getReferenceById(2L).getName());
        Assertions.assertNull(departmentRepository.getReferenceById(2L).getRentalOffice());

        final RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);

        Assertions.assertEquals("Office in Warsaw", rentalOfficeRepository.getReferenceById(1L).getName());
        Assertions.assertEquals(0, rentalOffice.getDepartments().size());

        //When
        Set<Department> departments = new HashSet<>();
        departments.add(department);
        departments.add(secondDepartment);
        rentalOffice.setDepartments(departments);

        Set<Department> resultDepartments = rentalOfficeRepository.getReferenceById(1L).getDepartments();

        //Then
        Assertions.assertEquals(2, resultDepartments.size());
        Assertions.assertEquals("DepartmentSecond", secondDepartment.getName());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteRentalOfficeWithDepartmentsTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);
        Assertions.assertEquals("DepartmentSecond", departmentRepository.getReferenceById(2L).getName());
        Assertions.assertNull(departmentRepository.getReferenceById(2L).getRentalOffice());

        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);
        Assertions.assertEquals("Office in Warsaw", rentalOfficeRepository.getReferenceById(1L).getName());
        Assertions.assertEquals(0, rentalOffice.getDepartments().size());

        //When
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);

        Assertions.assertEquals(2, rentalOffice.getDepartments().size());
        Assertions.assertEquals(rentalOffice, departmentRepository.findById(2L).get().getRentalOffice());
        Assertions.assertEquals("12-345", departmentRepository.findById(2L).get().getRentalOffice().getPostalCode());

        rentalOfficeRepository.deleteAll();
        final List<Department> departmentRepositoryAll = departmentRepository.findAll();

        //Then
        Assertions.assertEquals(0, departmentRepositoryAll.size());
        Assertions.assertEquals(0, rentalOfficeRepository.findAll().size());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteDepartmentFromRentalOfficeTest() {
        //Given
        final Department department = new Department("DepartmentFirst");
        final Department secondDepartment = new Department("DepartmentSecond");
        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);

        final RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        rentalOfficeRepository.save(rentalOffice);

        //When
        rentalOffice.addDepartment(department);
        rentalOffice.addDepartment(secondDepartment);
        Assertions.assertEquals(2, rentalOffice.getDepartments().size());
        Assertions.assertEquals(rentalOffice, departmentRepository.findById(2L).get().getRentalOffice());
        Assertions.assertEquals("12-345", departmentRepository.findById(2L).get().getRentalOffice().getPostalCode());

        final List<Department> departmentRepositoryAllBeforeDeleteDepartment = departmentRepository.findAll();
        Assertions.assertEquals(2, departmentRepositoryAllBeforeDeleteDepartment.size());

        rentalOffice.getDepartments().remove(department);
        final List<Department> departmentRepositoryAll = departmentRepository.findAll();
        //Then
        Assertions.assertFalse(departmentRepositoryAll.contains(department));
        Assertions.assertEquals(1, departmentRepositoryAll.size());

    }

}
