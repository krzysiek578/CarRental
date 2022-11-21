package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.PetrolType;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.Area;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.AreaManager;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.Car;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class AreaSuite {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    AreaManager areaManager;

    @AfterEach
    public void reset() {
        carRepository.deleteAll();
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCustomFindById() {
        //Given
        Area area = new Area("FirstArea");
        //When
        areaManager.save(area);
        Optional<Area> optionalArea = areaManager.findById(1L);
        //Then
        Assertions.assertTrue(optionalArea.isPresent());
    }
}
