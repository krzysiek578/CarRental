package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentManager extends GenericManagerBase<Department, Long> {
    public DepartmentManager(JpaRepository<Department, Long> jpaRepository) {
        super(jpaRepository);
    }
}
