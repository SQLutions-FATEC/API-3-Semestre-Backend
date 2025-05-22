package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c.tradeName FROM Company c WHERE c.id = :id")
    String getTradeNameById(@Param("id") Long id);
}