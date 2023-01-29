package com.app.portfolio.database.DatabaseAndSpringBoot.car;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.CarApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.CarDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.CarListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RestController
public class CarController extends CarApiController {

    private final CarManager carManager;
    private final CarMapperImpl carMapper;

    public CarController(final ObjectMapper objectMapper, final HttpServletRequest request, final CarManager carManager, final CarMapperImpl carMapper) {
        super(objectMapper, request);
        this.carManager = carManager;
        this.carMapper = carMapper;
    }


    @Override
    public ResponseEntity<CarDTO> car(@PathVariable("id") final String id) {
        return ResponseEntity.of(carManager.findById(Long.valueOf(id)).map(carMapper::mapToCarDTO));
    }

    @Override
    public ResponseEntity<CarDTO> createCar(@RequestBody final CarDTO body) {
        return ResponseEntity.ok(
                carMapper.mapToCarDTO(carManager.save(carMapper.mapToCar(body)))
        );
    }

    @Override
    public ResponseEntity<Boolean> deleteCar(@PathVariable("id") final String id) {
        if (!isNotBlank(id)) return ResponseEntity.notFound().build();
        carManager.delete(Long.valueOf(id));
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<CarListDTO> listCar() {
        //wiem że nie powinno tworzyć się teog obiektu ale nia mam pomysłu jak to inaczej zrobić, obstawiam że można jakoś collectorem ale nie wiem jak go do końca wykorzystywać
        final CarListDTO carDTOS = new CarListDTO();
        carDTOS.addAll(carManager.findAll().stream().map(carMapper::mapToCarDTO).toList());
        return ResponseEntity.ok(carDTOS);
    }

    @Override
    public ResponseEntity<CarDTO> updateCar(@PathVariable("id") final String id, @RequestBody final CarDTO body) {
        return ResponseEntity.ok(carMapper.mapToCarDTO(carManager.save(carMapper.mapToCar(body))));
    }


}
