package com.app.portfolio.database.DatabaseAndSpringBoot;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DatabaseAndSpringBootApplicationController {


    private final AreaRepository areaRepository;


    @GetMapping("/areas")
    public String test() {

        final List<Area> areaDao = getAreas();

        areaRepository.saveAll(areaDao);

        final List<Area> areaFromDatabase = areaRepository.findAll();
        return areaFromDatabase.stream()
                .map(Area::toString)
                .collect(Collectors.joining(", "));
    }

    private List<Area> getAreas() {
        return new ArrayList<>(
                List.of(
                        Area.builder()
                                .name("Area1")
                                .build(),
                        Area.builder()
                                .name("Area2")
                                .build(),
                        Area.builder()
                                .name("Area3")
                                .build()
                )
        );
    }


}
