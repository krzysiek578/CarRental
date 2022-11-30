package com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice;

import com.app.portfolio.database.DatabaseAndSpringBoot.generic.GenericManagerImpl;
import org.springframework.stereotype.Service;

@Service
public class RentalOfficeManager extends GenericManagerImpl<RentalOffice, Long> {
    public RentalOfficeManager(RentalOfficeRepository rentalOfficeRepository) {
        super(rentalOfficeRepository);
    }
}
