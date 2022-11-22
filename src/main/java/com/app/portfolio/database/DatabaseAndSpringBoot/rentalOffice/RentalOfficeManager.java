package com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalOfficeManager extends GenericManagerBase<RentalOffice, Long> {
    public RentalOfficeManager(JpaRepository<RentalOffice, Long> jpaRepository) {
        super(jpaRepository);
    }
}
