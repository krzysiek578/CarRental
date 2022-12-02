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
    @InjectMocks
    RentalOfficeManager rentalOfficeManager;

    @Mock
    RentalOfficeRepository rentalOfficeRepository;

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
        //When
        List<RentalOffice> rentalOffices = rentalOfficeManager.findAll();
        //Then
        Assertions.assertEquals(3, rentalOffices.size());
        Assertions.assertEquals("SecondOffice", rentalOffices.get(1).getName());
        Assertions.assertEquals("Cracow", rentalOffices.get(1).getCity());
        verify(rentalOfficeRepository, times(1)).findAll();
    }


    @Test
    public void findByIdTest() {
        //Given
        given(rentalOfficeRepository.findById(2L)).willReturn(Optional.of(new RentalOffice("ThirdOffice", "Gdynia", "Miejska", "12-345")));
        //When
        Optional<RentalOffice> rentalOffice = rentalOfficeManager.findById(2L);
        //Then
        RentalOffice rentalOfficeFromDatabase = rentalOffice.get();
        Assertions.assertEquals("Gdynia", rentalOfficeFromDatabase.getCity());
        Assertions.assertEquals("Gdynia", rentalOfficeFromDatabase.getCity());
        Assertions.assertEquals("Miejska", rentalOfficeFromDatabase.getStreet());
        Assertions.assertEquals("12-345", rentalOfficeFromDatabase.getPostalCode());

        verify(rentalOfficeRepository, times(1)).findById(2L);
    }


    @Test
    public void saveTestWithoutID() {
        //Given
        RentalOffice rentalOffice = new RentalOffice("ThirdOffice", "Gdynia", "Miejska", "12-345");
        given(rentalOfficeRepository.save(rentalOffice)).willReturn(rentalOffice);
        //When
        RentalOffice rentalOfficeSaved = rentalOfficeManager.save(rentalOffice);
        //Then
        Assertions.assertEquals("ThirdOffice", rentalOfficeSaved.getName());
        Assertions.assertEquals("Gdynia", rentalOfficeSaved.getCity());
        Assertions.assertEquals("Miejska", rentalOfficeSaved.getStreet());
        Assertions.assertEquals("12-345", rentalOfficeSaved.getPostalCode());
        verify(rentalOfficeRepository, times(1)).save(rentalOffice);
    }

    @Test
    public void saveTestWithID() {
        //Given
        RentalOffice rentalOffice = new RentalOffice(2L, "ThirdOffice", "Gdynia", "Miejska", "12-345", null, null);
        given(rentalOfficeRepository.save(rentalOffice)).willReturn(rentalOffice);
        //When
        RentalOffice rentalOfficeSaved = rentalOfficeManager.save(rentalOffice);
        //Then
        Assertions.assertEquals("ThirdOffice", rentalOfficeSaved.getName());
        Assertions.assertEquals("Gdynia", rentalOfficeSaved.getCity());
        Assertions.assertEquals("Miejska", rentalOfficeSaved.getStreet());
        Assertions.assertEquals("12-345", rentalOfficeSaved.getPostalCode());
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
        //When
        Optional<RentalOffice> changedRentalOffice = rentalOfficeManager.update(changeRentalOffice);
        //Then
        Assertions.assertEquals("ChangeOffice", changedRentalOffice.get().getName());
        Assertions.assertEquals("Gdynia", changedRentalOffice.get().getCity());
        Assertions.assertEquals("Miejska", changedRentalOffice.get().getStreet());
        Assertions.assertEquals("12-345", changedRentalOffice.get().getPostalCode());
        verify(rentalOfficeRepository, times(1)).save(changeRentalOffice);
    }




    @Test
    public void updateNotFoundObjectTest() {
        //Given
        given(rentalOfficeRepository.findById(2L)).willReturn(Optional.empty());
        RentalOffice changeRentalOffice = new RentalOffice(2L, "ChangeOffice", "Gdynia", "Miejska", "12-345", null, null);
        given(rentalOfficeRepository.save(changeRentalOffice)).willReturn(changeRentalOffice);
        //When
        Optional<RentalOffice> changedRentalOffice = rentalOfficeManager.update(changeRentalOffice);
        //Then
        Assertions.assertFalse(changedRentalOffice.isPresent());

        verify(rentalOfficeRepository, never()).save(changeRentalOffice);
    }

    @Test
    public void deleteFindObjectTest() {
        //Given
        given(rentalOfficeRepository.existsById(2L)).willReturn(true);
        //When
        rentalOfficeManager.delete(2L);
        //
        verify(rentalOfficeRepository, times(1)).deleteById(2L);
    }

    @Test
    public void deleteNotFindObjectTest() {
        //Given
        given(rentalOfficeRepository.existsById(2L)).willReturn(false);
        //When
        rentalOfficeManager.delete(2L);
        //Then
        verify(rentalOfficeRepository, never()).deleteById(2L);
    }
}
