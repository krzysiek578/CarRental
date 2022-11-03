package com.app.portfolio.database.DatabaseAndSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class DatabaseAndSpringBootAplicationController {

    @Autowired
    AreaRepository areaRepository;

    @GetMapping("/data")
    public String test() {

        List<Area> someAreas = getAreas();

        areaRepository.saveAll(someAreas);

        List<Area> areas1 = areaRepository.findAll();
        return areas1.stream()
                .map(Area::toString)
                .collect(Collectors.joining(", "));
    }

    private List<Area> getAreas() {
        return new ArrayList<>(
                List.of(
                        new Area("Area1"),
                        new Area("Area2"),
                        new Area("Area3"),
                        new Area("Area4")
                )
        );
    }


}
