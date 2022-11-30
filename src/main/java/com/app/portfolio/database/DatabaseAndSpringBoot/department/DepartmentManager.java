package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.generic.GenericManagerImpl;
import org.springframework.stereotype.Service;

@Service
public class DepartmentManager extends GenericManagerImpl<Department, Long> {
    public DepartmentManager(DepartmentRepository departmentRepository) {
        super(departmentRepository);
    }
}
