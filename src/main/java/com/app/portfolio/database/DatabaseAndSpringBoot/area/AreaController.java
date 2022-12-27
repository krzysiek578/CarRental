package com.app.portfolio.database.DatabaseAndSpringBoot.area;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.AreaDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AreaController {

    AreaManager areaManager;
    AreaMapperImpl areaMapper;

    AreaController(AreaManager areaManager, AreaMapperImpl areaMapper) {
        this.areaManager = areaManager;
        this.areaMapper = areaMapper;
    }

    @PostMapping(value = "/areas/add")
    public Area addAdrea(@RequestBody AreaDTO areaDTO) {
        Area areaFromRequest = areaMapper.mapToArea(areaDTO);
        return areaManager.save(areaFromRequest);
    }

    @GetMapping(value = "/areas")
    public List<AreaDTO> getAreas() {
        List<Area> areaDAOS = areaManager.findAll();
        List<AreaDTO> areaDTOS = new ArrayList<>();
        for (Area areaDAO : areaDAOS) {
            areaDTOS.add(areaMapper.maptoAreaDTO(areaDAO));
        }
        return areaDTOS;
    }

    @GetMapping(value = "/areas/{id}")
    public AreaDTO findByID(@PathVariable("id") Long id){
        Optional<Area> areaDAO = areaManager.findById(id);
        return areaDAO.map(area -> areaMapper.maptoAreaDTO(area)).orElse(null);
    }

    @DeleteMapping(value = "/areas/remove/{id}")
    public void deleteArea(@PathVariable("id") Long id){
        areaManager.delete(id);
    }


}
