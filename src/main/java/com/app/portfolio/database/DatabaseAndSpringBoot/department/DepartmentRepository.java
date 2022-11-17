package com.app.portfolio.database.DatabaseAndSpringBoot.department;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
