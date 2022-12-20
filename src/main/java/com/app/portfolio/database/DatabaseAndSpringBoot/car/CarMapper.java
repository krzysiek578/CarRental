package com.app.portfolio.database.DatabaseAndSpringBoot.car;

import com.app.portfolio.database.DatabaseAndSpringBoot.department.Department;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentMapper;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.CarDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = DepartmentMapper.class)
public interface CarMapper {

    @Mapping(target = "departments", expression = "java(this.departmentsToDepartmentDTOS(car.getDepartments()))")
    CarDTO mapToCarDTO (Car car);

    @InheritConfiguration(name = "mapToCarDTO")
    Car mapToCar (CarDTO carDTO);
    default List<DepartmentDTO> departmentsToDepartmentDTOS(Set<Department> departments){
        List<DepartmentDTO> result = new ArrayList<>();
        DepartmentDTO departmentDTO = new DepartmentDTO();
        if (departments != null){
            for (Department department : departments) {
                departmentDTO.setId(department.getId());
                departmentDTO.setName(department.getName());
                result.add(departmentDTO);
            }
        }
        return result;
    }
}
