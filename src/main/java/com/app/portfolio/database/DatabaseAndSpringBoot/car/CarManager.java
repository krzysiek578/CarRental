package com.app.portfolio.database.DatabaseAndSpringBoot.car;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CarManager extends GenericManagerBase<Car, Long> {
    public CarManager(CarRepository carRepository) {
        super(carRepository);
    }
}
