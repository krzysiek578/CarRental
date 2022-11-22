package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class AreaManager extends GenericManagerBase<Area, Long> {
    public AreaManager( JpaRepository<Area, Long> jpaRepository) {
        super(jpaRepository); //Nie do końca rozumiem to wstrzykiwanie
    }
}
