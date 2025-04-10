package com.sqlutions.altave.repository;

import com.sqlutions.altave.dto.ClockInSearchDTO;
import com.sqlutions.altave.entity.ClockIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockInRepository extends JpaRepository<ClockIn, Long> {

   @Query("SELECT c FROM ClockIn c")
   Page<ClockIn> searchClockIns(ClockInSearchDTO params, Pageable pageable);
}