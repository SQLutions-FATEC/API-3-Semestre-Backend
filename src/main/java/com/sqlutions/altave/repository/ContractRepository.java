package com.sqlutions.altave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.entity.Employee;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE c.employee = :employee " +
            "AND :data BETWEEN c.startDate AND c.endDate")
    Optional<Contract> findContractByEmployeeAndDate(@Param("employee") Employee employee, @Param("data") LocalDate data);

}
