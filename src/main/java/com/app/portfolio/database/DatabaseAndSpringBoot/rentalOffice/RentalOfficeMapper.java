package com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.RentalOfficeDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface RentalOfficeMapper {

    RentalOffice mapToRentalOffice(RentalOfficeDto rentalOfficeDto);

    RentalOfficeDto mapToRentalOfficeDTO(RentalOffice rentalOffice);
}
