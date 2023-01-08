package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.AreaApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AreaController extends AreaApiController {

    final private AreaManager areaManager;
    final private AreaMapperImpl areaMapper;

    public AreaController(ObjectMapper objectMapper, HttpServletRequest request, AreaManager areaManager, AreaMapperImpl areaMapper) {
        super(objectMapper, request);
        this.areaManager = areaManager;
        this.areaMapper = areaMapper;
    }

    @Override
    @GetMapping(value = "/areas/{id}")
    public ResponseEntity<AreaDTO> area(@PathVariable("id") final String id) {
        return ResponseEntity.of(areaManager.findById(Long.valueOf(id)).map(areaMapper::maptoAreaDTO));
    }

    @Override
    @PostMapping(value = "/areas/add")
    public ResponseEntity<AreaDTO> createArea(@RequestBody final AreaDTO body) {
        return new ResponseEntity<>(
                areaMapper.maptoAreaDTO(areaManager.save(areaMapper.mapToArea(body))), HttpStatus.OK
        );
    }
    @Override
    @DeleteMapping(value = "/areas/remove/{id}")
    public ResponseEntity<Boolean> deleteArea(@PathVariable("id") final String id) {
        if (id == null) return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        areaManager.delete(Long.valueOf(id));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @Override
    @GetMapping(value = "/areas")
    public ResponseEntity<AreaListDTO> listArea() {
        AreaListDTO areaDTOS = new AreaListDTO();
        for (Area areaDAO : areaManager.findAll()) {
            areaDTOS.add(areaMapper.maptoAreaDTO(areaDAO));
        }
        return new ResponseEntity<>(areaDTOS, HttpStatus.OK);
    }

    @Override
    @PatchMapping("/areas/update/{id}")
    public ResponseEntity<AreaDTO> updateArea(@PathVariable("id") final String id, @RequestBody final AreaDTO body) {
        Area areaFromRequest = areaMapper.mapToArea(body);
        return new ResponseEntity<>(
                areaMapper.maptoAreaDTO(
                        areaManager.findById(Long.valueOf(id))
                                .map(areaManager::save)
                                .orElse(
                                        areaManager.update(areaFromRequest)
                                        .orElse(null)
                                )
                ), HttpStatus.OK
        );


    }


}
