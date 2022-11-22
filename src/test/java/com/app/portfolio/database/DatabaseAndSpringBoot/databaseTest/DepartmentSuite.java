package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.area.Area;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.AreaRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.Department;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class DepartmentSuite {
    @Autowired
    CarRepository carRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AreaRepository areaRepository;

    @AfterEach
    public void clearDatabase() {
        departmentRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addSomeCarsToDepartment() {
        //Given
        final Department department = new Department("Test");
        departmentRepository.save(department);

        final Car car = new Car("BMW", "M3", PetrolType.ELECTRIC, true);
        final Car secondCar = new Car("Audi", "A3", PetrolType.ELECTRIC, true);
        carRepository.save(car);
        carRepository.save(secondCar);

        //When
        final Set<Car> carListFromDepartmentBeforeAdd = departmentRepository.findById(1L).get().getCars();
        Assertions.assertEquals(0, carListFromDepartmentBeforeAdd.size());

        Set<Car> cars = new HashSet<>();
        cars.add(car);
        cars.add(secondCar);
        department.setCars(cars);

        final Optional<Department> departmentOptional = departmentRepository.findById(1L);
        Assertions.assertTrue(departmentOptional.isPresent());

        final Department departmentFromDatabase = departmentOptional.get();
        final Set<Car> carListFromDepartmentAfterAdd = departmentFromDatabase.getCars();

        //Then
        Assertions.assertEquals("Test", departmentFromDatabase.getName());
        Assertions.assertEquals(2, carListFromDepartmentAfterAdd.size());
    }

    @Test
    @Transactional
    public void deleteSomeCarsFromDepartment() {
        //Given
        Car car = new Car("Audi", "A3", PetrolType.ELECTRIC, true);
        Department department = new Department("Test");
        departmentRepository.save(department);
        carRepository.save(car);

        //When
        final Set<Car> carListFromDepartmentBeforeAdd = departmentRepository.findById(1L).get().getCars(); //isPresent nie jest konieczne, zależy co sprawdzasz
        Assertions.assertEquals(0 , carListFromDepartmentBeforeAdd.size());
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        department.setCars(cars);

        final Optional<Department> departmentOptional = departmentRepository.findById(1L);
        Assertions.assertTrue(departmentOptional.isPresent());
        final Department departmentFromDatabase = departmentOptional.get();
        final Set<Car> carListFromDepartmentAfterAdd = departmentFromDatabase.getCars();
        final Set<Car> departmentCarListAfterDeleteCar = department.getCars();
        final List<Car> allCarsAfterDeleteCarFromDepartment = carRepository.findAll();
        //Then
        //Lista w Hibernate to persistantbag - nie ma kolejności
        Assertions.assertEquals("Test", departmentFromDatabase.getName());
        Assertions.assertEquals(1, carListFromDepartmentAfterAdd.size());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addAreaToDepartmentTest() {
        //Given
        Department department = new Department("TestDepartment");
        Area area = new Area("TestArea");

        areaRepository.save(area);
        Assertions.assertEquals(area, areaRepository.findById(1L).get());
        Assertions.assertEquals("TestArea", areaRepository.findById(1L).get().getName());
        Optional<Area> areaBeforeAction = areaRepository.findById(1L);
        Assertions.assertTrue(areaBeforeAction.isPresent());
        Assertions.assertEquals(0, areaBeforeAction.get().getDepartments().size());

        departmentRepository.save(department);
        Assertions.assertEquals(department, departmentRepository.findById(1L).get());
        Assertions.assertEquals("TestDepartment", departmentRepository.findById(1L).get().getName());
        Assertions.assertNull(departmentRepository.findById(1L).get().getArea());

        //When
        department.setArea(area);
        final Area areaInDepartmentAfterAddArea = departmentRepository.findById(1L).get().getArea();
        //Then
        Assertions.assertNotNull(areaInDepartmentAfterAddArea);
        Assertions.assertEquals("TestArea" ,areaInDepartmentAfterAddArea.getName());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteAreaFromDepartmentTest() {
        //Given
        Department department = new Department("TestDepartment");
        Department secondDepartment = new Department("TestDepartmentSecond");
        Area area = new Area("TestArea");

        areaRepository.save(area);
        Assertions.assertEquals(area, areaRepository.findById(1L).get());
        Assertions.assertEquals("TestArea", areaRepository.findById(1L).get().getName());
        Optional<Area> areaBeforeAction = areaRepository.findById(1L);
        Assertions.assertTrue(areaBeforeAction.isPresent());
        Assertions.assertEquals(0, areaBeforeAction.get().getDepartments().size());

        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);
        Assertions.assertEquals(department, departmentRepository.findById(1L).get());
        Assertions.assertEquals("TestDepartmentSecond", departmentRepository.findById(2L).get().getName());
        Assertions.assertNull(departmentRepository.findById(1L).get().getArea());

        //When
        department.addArea(area);
        secondDepartment.addArea(area);
        final Area areaInDepartmentAfterAddArea = departmentRepository.findById(1L).get().getArea();
        Assertions.assertNotNull(areaInDepartmentAfterAddArea);
        Assertions.assertEquals("TestArea" ,areaInDepartmentAfterAddArea.getName());
        department.removeArea();
        //Then
        Assertions.assertNull(department.getArea());
        Assertions.assertEquals(1, area.getDepartments().size());
    }


    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteAreaFromDepartmentLookFromAreaTest() {
        //Given
        Department department = new Department("TestDepartment");
        Department secondDepartment = new Department("TestDepartmentSecond");
        Area area = new Area("TestArea");

        areaRepository.save(area);
        Assertions.assertEquals(area, areaRepository.findById(1L).get());
        Assertions.assertEquals("TestArea", areaRepository.findById(1L).get().getName());
        Optional<Area> areaBeforeAction = areaRepository.findById(1L);
        Assertions.assertTrue(areaBeforeAction.isPresent());
        Assertions.assertEquals(0, areaBeforeAction.get().getDepartments().size());

        departmentRepository.save(department);
        departmentRepository.save(secondDepartment);
        Assertions.assertEquals(department, departmentRepository.findById(1L).get());
        Assertions.assertEquals("TestDepartmentSecond", departmentRepository.findById(2L).get().getName());
        Assertions.assertNull(departmentRepository.findById(1L).get().getArea());

        //When
        department.setArea(area);
        secondDepartment.setArea(area);
        final Area areaInFirstDepartmentAfterAddArea = departmentRepository.findById(1L).get().getArea();
        Assertions.assertNotNull(areaInFirstDepartmentAfterAddArea);
        Assertions.assertEquals("TestArea" ,areaInFirstDepartmentAfterAddArea.getName());
        department.setArea(null);
        secondDepartment.setArea(null);
        //Then
        Assertions.assertEquals(0, area.getDepartments().size());
        Assertions.assertNull(department.getArea());
        Assertions.assertEquals(1, areaRepository.findAll().size());
    }

}



//Przy manyToMany na set bo nie chcemy uplikatow
//przy manyToMany tabela posrenia, persistbag przetrzymuje duplikaty, a set nie przetrzymuje duplikatów kubryjński o JPA
//W naszym przykładzie sety wszędzie jeśli potrzebujesz duplikatów dajesz listę
//java arraylist linkedlist -> hibernate persistbag
//właściciel relacji wykonuje zapytania
//dodaje department -> dodjesz z autami
//nowe auta nie dodajesz z nowym departamentem
//auto przypisujesz do istniejacego departamentu dlatego department jest właścicielem relacji czyli właściciel nie ma mappedby
//dwukierunkowe: dlatego ze chce przegladac auta w jakim sa oddziale dlatego
//w departamencie chce obejrzec auta czyli departament ma liste aut (znowu dwukierunkowa)
//jesli chcesz tylko przegladac furki w departamencie to nie musisz dawac dwukierunkowej