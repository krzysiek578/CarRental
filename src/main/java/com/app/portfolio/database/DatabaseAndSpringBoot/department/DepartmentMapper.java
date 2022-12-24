package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface DepartmentMapper {

    Department mapToDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO mapToDepartmentsDTO(Department department);




}
