package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.area.Area;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.AreaManager;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.AreaRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AreaSuite {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    AreaManager areaManager;

    @AfterEach
    public void reset() {
        carRepository.deleteAll();
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCustomFindByIdAreaAndSaveArea() {
        //Given
        final Area area = new Area("FirstArea");
        //When
        final List<Area> areasBeforeAdd = areaRepository.findAll();
        Assertions.assertEquals(0, areasBeforeAdd.size());

        areaManager.save(area);
        final Optional<Area> optionalArea = areaManager.findById(1L);
        final List<Area> areasAfterAdd = areaRepository.findAll();
        //Then
        Assertions.assertEquals(1, areasAfterAdd.size());
        Assertions.assertTrue(optionalArea.isPresent());
        Assertions.assertEquals("FirstArea", area.getName());
    }


    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCustomSaveAreaWithId() {
        //Given
        final Area area = new Area(2L,"FirstArea",null);
        //When
        final List<Area> areasBeforeAdd = areaRepository.findAll();
        Assertions.assertEquals(0, areasBeforeAdd.size());

        areaManager.save(area);
        final Optional<Area> optionalArea = areaManager.findById(1L);
        final List<Area> areasAfterAdd = areaRepository.findAll();
        //Then
        Assertions.assertEquals(1, areasAfterAdd.size());
        Assertions.assertTrue(optionalArea.isPresent());
        Assertions.assertEquals("FirstArea", area.getName());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateArea() {
        //Given
        final Area area = new Area(1L, "FirstArea", null);
        //When
        final List<Area> areasBeforeAdd = areaRepository.findAll();
        Assertions.assertEquals(0, areasBeforeAdd.size());

        areaManager.save(area);
        final Optional<Area> optionalArea = areaManager.findById(1L);
        final List<Area> areasAfterAdd = areaRepository.findAll();
        Assertions.assertEquals(1, areasAfterAdd.size());
        Assertions.assertTrue(optionalArea.isPresent());
        Assertions.assertEquals("FirstArea", area.getName());

        final Area updateArea = new Area(1L, "UpdateArea", null);
        final Optional<Area> updated = areaManager.update(updateArea);
        Assertions.assertEquals("UpdateArea", updated.get().getName());

        final List<Area> areasAfterUpdate = areaRepository.findAll();
        final Optional<Area> areaAfterUpdate = areaRepository.findById(1L);

        //Then
        Assertions.assertEquals(1, areasAfterUpdate.size());
        Assertions.assertTrue(areaAfterUpdate.isPresent());
        Assertions.assertEquals("UpdateArea", areaAfterUpdate.get().getName());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testBadUpdateAreaReturnEmptyOptional() {
        //Given
        final Area area = new Area(1L, "FirstArea", null);
        //When
        final List<Area> areasBeforeAdd = areaRepository.findAll();
        Assertions.assertEquals(0, areasBeforeAdd.size());

        areaManager.save(area);
        Assertions.assertEquals("FirstArea", area.getName());

        Area updateArea = new Area(2L, "UpdateArea", null);
        Optional<Area> updated = areaManager.update(updateArea);
        Assertions.assertTrue(updated.isEmpty());

        final List<Area> areasAfterUpdate = areaRepository.findAll();
        final Optional<Area> areaAfterUpdate = areaRepository.findById(1L);

        //Then
        Assertions.assertEquals(1, areasAfterUpdate.size());
        Assertions.assertTrue(areaAfterUpdate.isPresent());
        Assertions.assertEquals("FirstArea", areaAfterUpdate.get().getName());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCustomDelete() {
        //Given
        final Area area = new Area("FirstArea");
        final Area areaSecond = new Area("SecondArea");
        //When
        final List<Area> areasBeforeAdd = areaRepository.findAll();
        Assertions.assertEquals(0, areasBeforeAdd.size());

        areaManager.save(area);
        areaManager.save(areaSecond);

        final Optional<Area> optionalArea = areaManager.findById(2L);
        final List<Area> areasAfterAdd = areaRepository.findAll();
        Assertions.assertEquals(2, areasAfterAdd.size());
        Assertions.assertEquals("SecondArea", optionalArea.get().getName());

        areaManager.delete(1L);
        final Optional<Area> optionalAreaAfterDelete = areaManager.findById(2L);
        final List<Area> areasAfterDelete = areaRepository.findAll();

        //Then
        Assertions.assertEquals(1, areasAfterDelete.size());
        Assertions.assertEquals("SecondArea", optionalAreaAfterDelete.get().getName());
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCustomFinAll() {
        //Given
        final Area area = new Area("FirstArea");
        final Area areaSecond = new Area("SecondArea");
        //When
        final List<Area> areasBeforeAdd = areaRepository.findAll();
        Assertions.assertEquals(0, areasBeforeAdd.size());

        areaManager.save(area);
        areaManager.save(areaSecond);

        final Optional<Area> optionalArea = areaManager.findById(2L);
        final List<Area> areasAfterAdd = areaRepository.findAll();
        Assertions.assertEquals(2, areasAfterAdd.size());
        Assertions.assertEquals("SecondArea", optionalArea.get().getName());


        final List<Area> areasAfterFindAll = areaManager.findAll();

        //Then
        Assertions.assertEquals(2, areasAfterFindAll.size());
        Assertions.assertEquals("FirstArea", areasAfterFindAll.get(0).getName());
    }

}
