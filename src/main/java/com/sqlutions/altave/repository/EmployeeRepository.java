package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDeletedFalse();
    Optional<Employee> findByEmployeeIdAndDeletedFalse(Long id);
}

