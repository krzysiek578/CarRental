package com.app.portfolio.database.DatabaseAndSpringBoot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalOfficeRepository extends JpaRepository<RentalOffice, Long> {
}
