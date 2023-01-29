package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.DepartmentApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RestController
public class DepartmentController extends DepartmentApiController {

    private final DepartmentManager departmentManager;
    private final DepartmentMapperImpl departmentMapper;

    public DepartmentController(final ObjectMapper objectMapper, final HttpServletRequest request, final DepartmentManager departmentManager, final DepartmentMapperImpl departmentMapper) {
        super(objectMapper, request);
        this.departmentManager = departmentManager;
        this.departmentMapper = departmentMapper;
    }


    @Override
    public ResponseEntity<DepartmentDTO> department(@PathVariable("id") final String id) {
        return ResponseEntity.of(departmentManager.findById(Long.valueOf(id)).map(departmentMapper::mapToDepartmentsDTO));
    }

    @Override
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody final DepartmentDTO body) {
        return ResponseEntity.ok(
                departmentMapper.mapToDepartmentsDTO(departmentManager.save(departmentMapper.mapToDepartment(body)))
        );
    }

    @Override
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable("id") final String id) {
        if (!isNotBlank(id)) return ResponseEntity.notFound().build();
        departmentManager.delete(Long.valueOf(id));
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<DepartmentListDTO> listDepartment() {
        final DepartmentListDTO departmentDTOS = new DepartmentListDTO();
        departmentDTOS.addAll(departmentManager.findAll().stream().map(departmentMapper::mapToDepartmentsDTO).toList());
        return ResponseEntity.ok(departmentDTOS);
    }

    @Override
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("id") final String id, @RequestBody final DepartmentDTO body) {
        return ResponseEntity.ok(
                departmentMapper.mapToDepartmentsDTO(departmentManager.save(departmentMapper.mapToDepartment(body)))
        );


    }


}
