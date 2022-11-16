package com.app.portfolio.database.DatabaseAndSpringBoot.deprtment;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
