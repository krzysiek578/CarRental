package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface DepartmentMapper {

    Department mapToDepartment(DepartmentDTO departmentDTO);
    Set<Department> mapToDepartments(List<DepartmentDTO> departmentDTOS);

    @InheritInverseConfiguration(name = "mapToDepartment")
    DepartmentDTO mapToDepartmentsDTO(Department department);

    @InheritInverseConfiguration(name = "mapToDepartments")
    List<DepartmentDTO> mapToDepartmentDTOS(Set<Department> departments);

}
