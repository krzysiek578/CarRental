package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.area.Area;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.AreaManager;
import com.app.portfolio.database.DatabaseAndSpringBoot.area.AreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class AreaManagerSuite {
    @InjectMocks
    AreaManager areaManager;

    @Mock
    AreaRepository areaRepository;

    @Test
    public void findAllTest() {
        //Given
        given(areaRepository.findAll()).willReturn(new ArrayList<>(
                        List.of(
                                new Area("FirstTestArea"),
                                new Area("SecondTestArea"),
                                new Area("ThirdTestArea")
                        )
                )
        );
        //When
        List<Area> areas = areaManager.findAll();
        //Then
        Assertions.assertEquals(3, areas.size());
        Assertions.assertEquals("SecondTestArea", areas.get(1).getName());
        verify(areaRepository, times(1)).findAll();
    }


    @Test
    public void findByIdTest() {
        //Given
        given(areaRepository.findById(2L)).willReturn(Optional.of(new Area("FindMockArea")));
        //When
        Optional<Area> area = areaManager.findById(2L);
        //Then

        Assertions.assertEquals("FindMockArea", area.get().getName());

        verify(areaRepository, times(1)).findById(2L);
    }


    @Test
    public void saveTestWithoutID() {
        //Given
        Area area = new Area("ToSave");
        given(areaRepository.save(area)).willReturn(area);
        //When
        Area areaSaved = areaManager.save(area);
        //Then
        Assertions.assertEquals("ToSave", areaSaved.getName());
        verify(areaRepository, times(1)).save(area);
    }

    @Test
    public void saveTestWithID() {
        //Given
        Area area = new Area(2L, "ToSave", null);
        given(areaRepository.save(area)).willReturn(area);
        //When
        Area areaSaved = areaManager.save(area);
        //Then
        Assertions.assertEquals("ToSave", areaSaved.getName());
        Assertions.assertNull(areaSaved.getId()); //null = niceMock
        verify(areaRepository, times(1)).save(area);
    }


    @Test
    public void updateTest() {
        //Given
        Area area = new Area(2L, "AreaName", null);
        given(areaRepository.findById(2L)).willReturn(Optional.of(area));
        Area changeArea = new Area(2L, "ChangeArea", null);
        given(areaRepository.save(changeArea)).willReturn(changeArea);
        //When
        Optional<Area> changedArea = areaManager.update(changeArea);
        //Then
        Assertions.assertEquals("ChangeArea", changedArea.get().getName());

        verify(areaRepository, times(1)).save(changeArea);
    }

    @Test
    public void updateNotFoundObjectTest() {
        //Given
        given(areaRepository.findById(2L)).willReturn(Optional.empty());
        Area changeArea = new Area(2L, "ChangeArea", null);
        given(areaRepository.save(changeArea)).willReturn(changeArea);
        //When
        Optional<Area> changedArea = areaManager.update(changeArea);
        //Then
        Assertions.assertFalse(changedArea.isPresent());

        verify(areaRepository, never()).save(changeArea);
    }

    @Test
    public void deleteFindObjectTest() {
        //Given
        given(areaRepository.existsById(2L)).willReturn(true);
        //When
        areaManager.delete(2L);
        //Then
        verify(areaRepository, times(1)).deleteById(2L);
    }

    @Test
    public void deleteNotFindObjectTest() {
        //Given
        given(areaRepository.existsById(2L)).willReturn(false);
        //When
        areaManager.delete(2L);
        //Then
        verify(areaRepository, never()).deleteById(2L);
    }
}
