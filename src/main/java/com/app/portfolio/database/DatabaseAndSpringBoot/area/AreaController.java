package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.AreaApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RestController
public class AreaController extends AreaApiController {

    private final AreaManager areaManager;
    private final AreaMapperImpl areaMapper;

    public AreaController(final ObjectMapper objectMapper, final HttpServletRequest request, final AreaManager areaManager, final AreaMapperImpl areaMapper) {
        super(objectMapper, request);
        this.areaManager = areaManager;
        this.areaMapper = areaMapper;
    }

    @Override
    public ResponseEntity<AreaDTO> area(@PathVariable("id") final String id) {
        return ResponseEntity.of(areaManager.findById(Long.valueOf(id)).map(areaMapper::maptoAreaDTO));
    }

    @Override
    public ResponseEntity<AreaDTO> createArea(@RequestBody final AreaDTO body) {
        return ResponseEntity.ok(
                areaMapper.maptoAreaDTO(areaManager.save(areaMapper.mapToArea(body)))
        );
    }

    @Override
    public ResponseEntity<Boolean> deleteArea(@PathVariable("id") final String id) {
        if (!isNotBlank(id)) return ResponseEntity.notFound().build();
        areaManager.delete(Long.valueOf(id));
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<AreaListDTO> listArea() {
        final AreaListDTO areaDTOS = new AreaListDTO();
        areaDTOS.addAll(areaManager.findAll().stream().map(areaMapper::maptoAreaDTO).toList());
        return ResponseEntity.ok(areaDTOS);
    }

    @Override
    public ResponseEntity<AreaDTO> updateArea(@PathVariable("id") final String id, @RequestBody final AreaDTO body) {
        return ResponseEntity.ok(
                areaMapper.maptoAreaDTO(areaManager.save(areaMapper.mapToArea(body)))
        );
    }


}
