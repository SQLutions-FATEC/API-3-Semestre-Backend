package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByDeletedAtIsNull();

    Optional<Company> findByIdAndDeletedAtIsNull(Long id);

}
