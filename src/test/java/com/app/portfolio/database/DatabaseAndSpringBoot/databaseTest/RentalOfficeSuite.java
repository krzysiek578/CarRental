package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.RentalOffice;
import com.app.portfolio.database.DatabaseAndSpringBoot.RentalOfficeRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.jpa.provider.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Persistent
public class RentalOfficeSuite {


    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RentalOfficeRepository rentalOfficeRepository;

    @AfterEach
    public void reset() {
        rentalOfficeRepository.deleteAll();
    }

    @Test
    public void addRentalOfficeTest() {
        //Given
        RentalOffice rentalOffice = new RentalOffice("Office in Warsaw", "Warsaw", "Ul.Trakt Brzeski", "12-345");
        //When
        sessionFactory.openSession();

        rentalOfficeRepository.save(rentalOffice);
        //Then
        Assertions.assertTrue(rentalOfficeRepository.existsById(rentalOffice.getId()));
        //Assertions.assertEquals(rentalOffice, rentalOfficeRepository.findById(rentalOffice.getId()).get());
    }


}
