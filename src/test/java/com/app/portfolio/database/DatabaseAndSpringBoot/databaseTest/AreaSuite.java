package com.app.portfolio.database.DatabaseAndSpringBoot.databaseTest;

import com.app.portfolio.database.DatabaseAndSpringBoot.CarRepository;
import com.app.portfolio.database.DatabaseAndSpringBoot.DepartmentRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
