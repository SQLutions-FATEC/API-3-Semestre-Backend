package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

