package com.app.portfolio.database.DatabaseAndSpringBoot.car;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.CarApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.CarDTO;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.CarListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PathVariable;
=======
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> origin/feature/zad10
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CarController  extends CarApiController {

    final private CarManager carManager;
    final private CarMapperImpl carMapper;

    public CarController(final ObjectMapper objectMapper, final HttpServletRequest request, final CarManager carManager, final CarMapperImpl carMapper) {
        super(objectMapper, request);
        this.carManager = carManager;
        this.carMapper = carMapper;
    }
    

    @Override
<<<<<<< HEAD
=======
    @GetMapping(value = "/cars/{id}")
>>>>>>> origin/feature/zad10
    public ResponseEntity<CarDTO> car(@PathVariable("id") final String id) {
        return ResponseEntity.of(carManager.findById(Long.valueOf(id)).map(carMapper::mapToCarDTO));
    }

    @Override
<<<<<<< HEAD
=======
    @PostMapping(value = "/cars/add")
>>>>>>> origin/feature/zad10
    public ResponseEntity<CarDTO> createCar(@RequestBody final CarDTO body) {
        return new ResponseEntity<>(
                carMapper.mapToCarDTO(carManager.save(carMapper.mapToCar(body))), HttpStatus.OK
        );
    }
    @Override
<<<<<<< HEAD
=======
    @DeleteMapping(value = "/cars/remove/{id}")
>>>>>>> origin/feature/zad10
    public ResponseEntity<Boolean> deleteCar(@PathVariable("id") final String id) {
        if (id == null) return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        carManager.delete(Long.valueOf(id));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @Override
<<<<<<< HEAD
=======
    @GetMapping(value = "/cars")
>>>>>>> origin/feature/zad10
    public ResponseEntity<CarListDTO> listCar() {
        CarListDTO CarDTOS = new CarListDTO();
        for (Car CarDAO : carManager.findAll()) {
            CarDTOS.add(carMapper.mapToCarDTO(CarDAO));
        }
        return new ResponseEntity<>(CarDTOS, HttpStatus.OK);
    }

    @Override
<<<<<<< HEAD
=======
    @PatchMapping("/cars/update/{id}")
>>>>>>> origin/feature/zad10
    public ResponseEntity<CarDTO> updateCar(@PathVariable("id") final String id, @RequestBody final CarDTO body) {
        Car CarFromRequest = carMapper.mapToCar(body);
        return new ResponseEntity<>(
                carMapper.mapToCarDTO(
                        carManager.findById(Long.valueOf(id))
                                .map(carManager::save)
                                .orElse(
                                        carManager.update(CarFromRequest)
                                                .orElse(null)
                                )
                ), HttpStatus.OK
        );


    }


}
