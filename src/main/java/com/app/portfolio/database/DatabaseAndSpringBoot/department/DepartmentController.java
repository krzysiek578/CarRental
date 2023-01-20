package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.DepartmentApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.DepartmentListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DepartmentController  extends DepartmentApiController {

    final private DepartmentManager departmentManager;
    final private DepartmentMapperImpl departmentMapper;

    public DepartmentController(final ObjectMapper objectMapper, final HttpServletRequest request, final DepartmentManager departmentManager, final DepartmentMapperImpl departmentMapper) {
        super(objectMapper, request);
        this.departmentManager = departmentManager;
        this.departmentMapper = departmentMapper;
    }


    @Override
    public ResponseEntity<DepartmentDTO> department (@PathVariable("id") final String id) {
        return ResponseEntity.of(departmentManager.findById(Long.valueOf(id)).map(departmentMapper::mapToDepartmentsDTO));
    }

    @Override
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody final DepartmentDTO body) {
        return new ResponseEntity<>(
                departmentMapper.mapToDepartmentsDTO(departmentManager.save(departmentMapper.mapToDepartment(body))), HttpStatus.OK
        );
    }
    @Override
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable("id") final String id) {
        if (id == null) return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        departmentManager.delete(Long.valueOf(id));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<DepartmentListDTO> listDepartment() {
        DepartmentListDTO DepartmentDTOS = new DepartmentListDTO();
        for (Department departmentDAO : departmentManager.findAll()) {
            DepartmentDTOS.add(departmentMapper.mapToDepartmentsDTO(departmentDAO));
        }
        return new ResponseEntity<>(DepartmentDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("id") final String id, @RequestBody final DepartmentDTO body) {
        Department DepartmentFromRequest = departmentMapper.mapToDepartment(body);
        return new ResponseEntity<>(
                departmentMapper.mapToDepartmentsDTO(
                        departmentManager.findById(Long.valueOf(id))
                                .map(departmentManager::save)
                                .orElse(
                                        departmentManager.update(DepartmentFromRequest)
                                                .orElse(null)
                                )
                ), HttpStatus.OK
        );


    }


}
