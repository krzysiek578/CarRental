package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;


import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOffice;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOfficeManager;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.RentalOfficeRepository;
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
public class RentalOfficeManagerSuite {
    @Mock
    RentalOfficeRepository rentalOfficeRepository;
    
    @InjectMocks
    RentalOfficeManager rentalOfficeManager;

    @Test
    public void findAllTest() {
        //Given
        given(rentalOfficeRepository.findAll()).willReturn(new ArrayList<>(
                        List.of(
                                new RentalOffice("FirstOffice", "Warsaw", "Walecznych", "12-345"),
                                new RentalOffice("SecondOffice", "Cracow", "Kowalska", "94-231"),
                                new RentalOffice("ThirdOffice", "Gdynia", "Miejska", "12-345")
                        )
                )
        );
        //Then
        List<RentalOffice> rentalOffices = rentalOfficeManager.findAll();
        //When
        Assertions.assertEquals(3, rentalOffices.size());
        Assertions.assertEquals("SecondOffice", rentalOffices.get(1).getName());
        verify(rentalOfficeRepository, times(1)).findAll();
    }


    @Test
    public void findByIdTest() {
        //Given
        given(rentalOfficeRepository.findById(2L)).willReturn(Optional.of(new RentalOffice("ThirdOffice", "Gdynia", "Miejska", "12-345")));
        //Then
        Optional<RentalOffice> rentalOffice = rentalOfficeManager.findById(2L);
        //When
        rentalOffice.ifPresent(a -> {
            Assertions.assertEquals("Gdynia", a.getCity());
        });
        verify(rentalOfficeRepository, times(1)).findById(2L);
    }


    @Test
    public void saveTestWithoutID() {
        //Given
        RentalOffice rentalOffice = new RentalOffice("ThirdOffice", "Gdynia", "Miejska", "12-345");
        given(rentalOfficeRepository.save(rentalOffice)).willReturn(rentalOffice);
        //Then
        RentalOffice rentalOfficeSaved = rentalOfficeManager.save(rentalOffice);
        //When
        Assertions.assertEquals("Miejska", rentalOfficeSaved.getStreet());
        verify(rentalOfficeRepository, times(1)).save(rentalOffice);
    }

    @Test
    public void saveTestWithID() {
        //Given
        RentalOffice rentalOffice = new RentalOffice(2L, "ThirdOffice", "Gdynia", "Miejska", "12-345", null, null);
        given(rentalOfficeRepository.save(rentalOffice)).willReturn(rentalOffice);
        //Then
        RentalOffice rentalOfficeSaved = rentalOfficeManager.save(rentalOffice);
        //When
        Assertions.assertEquals("ThirdOffice", rentalOfficeSaved.getName());
        Assertions.assertNull(rentalOfficeSaved.getId()); //null = niceMock
        verify(rentalOfficeRepository, times(1)).save(rentalOffice);
    }


    @Test
    public void updateTest() {
        //Given
        RentalOffice rentalOffice = new RentalOffice(2L, "ThirdOffice", "Gdynia", "Miejska", "12-345", null, null);
        given(rentalOfficeRepository.findById(2L)).willReturn(Optional.of(rentalOffice));
        RentalOffice changeRentalOffice = new RentalOffice(2L, "ChangeOffice", "Gdynia", "Miejska", "12-345", null, null);
        given(rentalOfficeRepository.save(changeRentalOffice)).willReturn(changeRentalOffice);
        //Then
        Optional<RentalOffice> changedRentalOffice = rentalOfficeManager.update(changeRentalOffice);
        //When
        changedRentalOffice.ifPresent(a -> {
            Assertions.assertEquals("ChangeOffice", a.getName());
        });
        verify(rentalOfficeRepository, times(1)).save(changeRentalOffice);
    }

    @Test
    public void deleteFindObjectTest() {
        //Given
        given(rentalOfficeRepository.existsById(2L)).willReturn(true);
        //Then
        rentalOfficeManager.delete(2L);
        //
        verify(rentalOfficeRepository, times(1)).deleteById(2L);
        verify(rentalOfficeRepository).deleteById(2L);
    }

    @Test
    public void deleteNotFindObjectTest() {
        //Given
        given(rentalOfficeRepository.existsById(2L)).willReturn(false);
        //Then
        rentalOfficeManager.delete(2L);
        //When
        verify(rentalOfficeRepository, never()).deleteById(2L);
    }
}
