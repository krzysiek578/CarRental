package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.area.Area;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AreaRepository extends JpaRepository<Area, Long> {
}
