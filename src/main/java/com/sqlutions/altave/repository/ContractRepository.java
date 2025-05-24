package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.entity.Employee;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE c.employee = :employee " +
            "AND :data BETWEEN c.startDate AND c.endDate")
    Optional<Contract> findContractByEmployeeAndDate(@Param("employee") Employee employee, @Param("data") LocalDate data);

    List<Contract> findByCompanyAndDeletedAtIsNull(Company company);

    List<Contract> findByCompany(Company company);

    @Query("SELECT c FROM Contract c WHERE c.employee = :employee AND " +
            "(:startDate BETWEEN c.startDate AND c.endDate OR " +
            ":endDate BETWEEN c.startDate AND c.endDate OR " +
            "c.startDate BETWEEN :startDate AND :endDate OR " +
            "c.endDate BETWEEN :startDate AND :endDate)")
    List<Contract> findOverlappingContractsByEmployee(@Param("employee") Employee employee,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Contract c WHERE " +
            "c.employee.id = :employeeId AND " +
            "c.startDate <= :referenceDate AND " +
            "(c.endDate IS NULL OR c.endDate >= :referenceDate)")
    List<Contract> findActiveContractsByEmployee(@Param("employeeId") Long employeeId,
                                                 @Param("referenceDate") LocalDate referenceDate);
}
