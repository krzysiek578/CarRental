package com.app.portfolio.database.DatabaseAndSpringBoot.car;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.CarDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CarMapper {

    Car mapToCar(CarDTO carDTO);

    CarDTO mapToCarDTO(Car car);
}
