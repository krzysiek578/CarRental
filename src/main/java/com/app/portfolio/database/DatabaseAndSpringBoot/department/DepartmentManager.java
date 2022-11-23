package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import com.app.portfolio.database.DatabaseAndSpringBoot.GenericManagerBase;
import org.springframework.stereotype.Service;

@Service
public class DepartmentManager extends GenericManagerBase<Department, Long> {
    public DepartmentManager(DepartmentRepository departmentRepository) {
        super(departmentRepository);
    }
}
