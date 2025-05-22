package com.sqlutions.altave.repository;

import com.sqlutions.altave.dto.analytics.ExpiringContract;
import com.sqlutions.altave.dto.analytics.IncompleteClockIn;
import com.sqlutions.altave.entity.ClockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<ClockIn, Long> {

    @Query("SELECT COUNT(c) " +
            "FROM ClockIn c " +
            "JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate " +
            "WHERE c.dateTimeIn IS NOT NULL AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countClockInWithIn(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(c) " +
            "FROM ClockIn c " +
            "JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate " +
            "WHERE c.dateTimeOut IS NOT NULL AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countClockInWithOut(@Param("companyId") Long companyId);

    @Query(value = "SELECT r.name AS role_name, SUM(EXTRACT(EPOCH FROM (c.date_time_out - c.date_time_in))/3600) AS total_hours " +
            "FROM clock_in c " +
            "JOIN contract ct ON c.date_time_in BETWEEN ct.start_date AND ct.end_date " +
            "JOIN role r ON ct.role_id = r.id " +
            "WHERE c.date_time_in IS NOT NULL AND c.date_time_out IS NOT NULL " +
            "AND (:companyId IS NULL OR ct.company_id = :companyId) " +
            "GROUP BY r.id, r.name", nativeQuery = true)
    List<Object[]> getHoursWorkedByRole(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(DISTINCT e.id) " +
            "FROM Employee e JOIN Contract ct ON e.id = ct.employee.id " +
            "WHERE e.sex = 'M' AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countMaleWorkers(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(DISTINCT e.id) " +
            "FROM Employee e JOIN Contract ct ON e.id = ct.employee.id " +
            "WHERE e.sex = 'F' AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countFemaleWorkers(@Param("companyId") Long companyId);

    @Query("SELECT new com.sqlutions.altave.dto.analytics.ExpiringContract(ct.contractId, e.employeeName, ct.endDate) " +
            "FROM Contract ct JOIN ct.employee e " +
            "WHERE (:companyId IS NULL OR ct.company.id = :companyId) AND ct.endDate BETWEEN CURRENT_DATE AND :twoMonthsFromNow " +
            "ORDER BY ct.endDate ASC")
    List<ExpiringContract> getExpiringContracts(@Param("companyId") Long companyId,
                                                @Param("twoMonthsFromNow") LocalDate twoMonthsFromNow);

    @Query("SELECT new com.sqlutions.altave.dto.analytics.IncompleteClockIn(c.clockInId, e.employeeName, c.dateTimeIn, c.dateTimeOut) " +
            "FROM ClockIn c JOIN c.employee e " +
            "JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate " +
            "WHERE ((c.dateTimeIn IS NOT NULL AND c.dateTimeOut IS NULL) OR (c.dateTimeIn IS NULL AND c.dateTimeOut IS NOT NULL)) " +
            "AND (c.dateTimeIn >= :since OR c.dateTimeOut >= :since) AND (:companyId IS NULL OR ct.company.id = :companyId)")
    List<IncompleteClockIn> getIncompleteClockIns(@Param("companyId") Long companyId,
                                                  @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(DISTINCT c.employee.id) " +
            "FROM ClockIn c " +
            "JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate " +
            "WHERE EXTRACT(HOUR FROM c.dateTimeIn) BETWEEN 0 AND 7 AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countMidnightToMorning(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(DISTINCT c.employee.id) " +
            "FROM ClockIn c " +
            "JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate " +
            "WHERE EXTRACT(HOUR FROM c.dateTimeIn) BETWEEN 8 AND 15 AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countMorningToAfternoon(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(DISTINCT c.employee.id) " +
            "FROM ClockIn c " +
            "JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate " +
            "WHERE EXTRACT(HOUR FROM c.dateTimeIn) BETWEEN 16 AND 23 AND (:companyId IS NULL OR ct.company.id = :companyId)")
    int countAfternoonToNight(@Param("companyId") Long companyId);
}
