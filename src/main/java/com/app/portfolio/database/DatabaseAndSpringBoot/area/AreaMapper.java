package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AreaMapper {
    Area mapToArea(AreaDTO areaDTO);
}
