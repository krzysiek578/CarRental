package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;

import org.springframework.stereotype.Service;


@Service
public class AreaManager extends GenericManagerBase<Area, Long> {
    public AreaManager( AreaRepository areaRepository) {
        super(areaRepository);
    }
}
