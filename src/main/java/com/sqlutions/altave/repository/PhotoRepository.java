package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByEmployee_EmployeeId(Long employeeId);
}

