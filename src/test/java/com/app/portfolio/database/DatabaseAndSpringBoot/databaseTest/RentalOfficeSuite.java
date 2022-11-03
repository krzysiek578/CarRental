package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.RentalOffice;
import com.app.portfolio.database.DatabaseAndSpringBoot.RentalOfficeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RentalOfficeSuite {
    @Autowired
    RentalOfficeRepository rentalOfficeRepository;

    @AfterEach
    public void reset() {
        rentalOfficeRepository.deleteAll();
    }

    @Test
    public void testAddRentalOffice() {
        //Given
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When

        //Then
    }
}
