package com.app.portfolio.database.DatabaseAndSpringBoot.car;

import com.app.portfolio.database.DatabaseAndSpringBoot.generic.GenericManagerImpl;
import org.springframework.stereotype.Service;

@Service
public class CarManager extends GenericManagerImpl<Car, Long> {
    public CarManager(CarRepository carRepository) {
        super(carRepository);
    }
}
