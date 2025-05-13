package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.ClockIn;
import com.sqlutions.altave.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClockInRepository extends JpaRepository<ClockIn, Long> {
   List<ClockIn> findByEmployee(Employee employee);
   List<ClockIn> findAllByOrderByDateTimeInDesc();
}