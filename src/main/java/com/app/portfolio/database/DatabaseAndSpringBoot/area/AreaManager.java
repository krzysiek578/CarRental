package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.generic.GenericManagerImpl;

import org.springframework.stereotype.Service;


@Service
public class AreaManager extends GenericManagerImpl<Area, Long> {
    public AreaManager( AreaRepository areaRepository) {
        super(areaRepository);
    }
}
