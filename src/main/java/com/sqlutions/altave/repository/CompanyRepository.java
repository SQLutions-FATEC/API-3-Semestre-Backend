package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c.tradeName FROM Company c WHERE c.id = :id")
    String getTradeNameById(@Param("id") Long id);

    List<Company> findByDeletedAtIsNull();

    Optional<Company> findByIdAndDeletedAtIsNull(Long id);

}
