package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.deletedAt IS NULL")
    List<Employee> findAllActive();

    @Query("SELECT e FROM Employee e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<Employee> findByIdAndNotDeleted(Long id);
}

