package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface AreaMapper {

    Area mapToArea(AreaDTO areaDTO);

    AreaDTO maptoAreaDTO(Area area);
}
