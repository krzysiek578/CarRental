package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.car.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.department.DepartmentRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AreaSuite {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DepartmentRepository departmentRepository;

    @AfterEach
    public void reset() {
        carRepository.deleteAll();
    }


}
