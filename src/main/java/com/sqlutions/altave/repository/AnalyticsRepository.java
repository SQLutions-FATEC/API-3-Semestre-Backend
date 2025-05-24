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

    @Query("""
            SELECT COUNT(c)
                FROM ClockIn c
                JOIN Contract ct ON c.dateTimeIn BETWEEN ct.startDate AND ct.endDate
            WHERE c.dateTimeIn IS NOT NULL
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND (c.dateTimeIn < CURRENT_TIMESTAMP AND c.dateTimeIn >= :startDate)
                AND c.employee.id = ct.employee.id
                AND c.employee.deletedAt IS NULL
            """)
    Integer countClockInWithIn(@Param("companyId") Long companyId,
                               @Param("startDate") LocalDateTime startDate);

    @Query("""
            SELECT COUNT(c)
                FROM ClockIn c
                JOIN Contract ct ON c.dateTimeOut BETWEEN ct.startDate AND ct.endDate
            WHERE c.dateTimeOut IS NOT NULL
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND (c.dateTimeOut < CURRENT_TIMESTAMP
                AND c.dateTimeOut >= :startDate)
                AND c.employee.id = ct.employee.id
                AND c.employee.deletedAt IS NULL
            """)
    Integer countClockInWithOut(@Param("companyId") Long companyId,
                                @Param("startDate") LocalDateTime startDate);

    @Query(value =
           """
            SELECT r.name AS role_name, SUM(EXTRACT(EPOCH FROM (c.date_time_out - c.date_time_in))/3600) AS total_hours
                FROM clock_in c
                    JOIN contract ct ON c.employee_id = ct.employee_id
                    JOIN employee e ON ct.employee_id = e.id
                    JOIN role r ON ct.role_id = r.id
            WHERE c.date_time_in IS NOT NULL AND c.date_time_out IS NOT NULL
                AND c.date_time_out BETWEEN ct.start_date AND ct.end_date
                AND c.date_time_out < :endDate
                AND c.date_time_in >= :startDate
                AND ct.company_id = :companyId
                AND e.deleted_at IS NULL
            GROUP BY r.id;
            """, nativeQuery = true)
    List<Object[]> getHoursWorkedByRole(@Param("companyId") Long companyId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT COUNT(DISTINCT e.id)
                FROM Employee e JOIN Contract ct ON e.id = ct.employee.id
            WHERE e.sex = 'M'
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND CURRENT_DATE BETWEEN ct.startDate AND ct.endDate
                AND e.deletedAt IS NULL
            """)
    Integer countMaleWorkers(@Param("companyId") Long companyId);

    @Query("""
            SELECT COUNT(DISTINCT e.id)
                FROM Employee e JOIN Contract ct ON e.id = ct.employee.id
            WHERE e.sex = 'F'
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND CURRENT_DATE BETWEEN ct.startDate AND ct.endDate
                AND e.deletedAt IS NULL
            """)
    Integer countFemaleWorkers(@Param("companyId") Long companyId);

    @Query("""
            SELECT new com.sqlutions.altave.dto.analytics.ExpiringContract(ct.contractId, e.registerNumber, e.employeeName, ct.company.tradeName, ct.endDate)
                FROM Contract ct JOIN ct.employee e
            WHERE (:companyId IS NULL OR ct.company.id = :companyId)
                AND ct.endDate > CURRENT_DATE
                AND e.deletedAt IS NULL
            ORDER BY ct.endDate ASC
            LIMIT 5
            """)
    List<ExpiringContract> getExpiringContracts(@Param("companyId") Long companyId);

    @Query("""
            SELECT new com.sqlutions.altave.dto.analytics.IncompleteClockIn(c.clockInId, e.registerNumber, e.employeeName, ct.role.name, ct.company.tradeName, c.dateTimeIn, c.dateTimeOut)
                FROM ClockIn c
                    JOIN c.employee e
                    JOIN e.contracts ct
            WHERE ((c.dateTimeIn IS NOT NULL AND c.dateTimeOut IS NULL) OR (c.dateTimeIn IS NULL AND c.dateTimeOut IS NOT NULL))
                AND (c.dateTimeIn BETWEEN ct.startDate AND ct.endDate OR c.dateTimeOut BETWEEN ct.startDate AND ct.endDate)
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND (c.dateTimeIn <= :since OR c.dateTimeOut <= :since)
                AND e.deletedAt IS NULL
            """)
    List<IncompleteClockIn> getIncompleteClockIns(@Param("companyId") Long companyId, @Param("since") LocalDateTime since);

    @Query("""
            SELECT COUNT(DISTINCT c.employee.id) FROM ClockIn c
                JOIN Contract ct ON c.dateTimeOut BETWEEN ct.startDate AND ct.endDate
            WHERE (EXTRACT(HOUR FROM c.dateTimeIn) BETWEEN 0 AND 6 OR EXTRACT(HOUR FROM c.dateTimeIn) = 23)
                AND ct.employee.id = c.employee.id
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND c.dateTimeIn >= :since
                AND c.dateTimeOut < CURRENT_TIMESTAMP
                AND c.employee.deletedAt IS NULL
            """)
    int countMidnightToMorning(@Param("companyId") Long companyId, @Param("since") LocalDateTime since);

    @Query("""
            SELECT COUNT(DISTINCT c.employee.id) FROM ClockIn c
                JOIN Contract ct ON c.dateTimeOut BETWEEN ct.startDate AND ct.endDate
            WHERE EXTRACT(HOUR FROM c.dateTimeIn) BETWEEN 7 AND 14
                AND ct.employee.id = c.employee.id
                AND (:companyId IS NULL OR ct.company.id = :companyId)
                AND c.dateTimeIn >= :since
                AND c.dateTimeOut < CURRENT_TIMESTAMP
                AND c.employee.deletedAt IS NULL
            """)
    int countMorningToAfternoon(@Param("companyId") Long companyId, @Param("since") LocalDateTime since);

    @Query("""
            SELECT COUNT(DISTINCT c.employee.id) FROM ClockIn c
                JOIN Contract ct ON c.dateTimeOut BETWEEN ct.startDate AND ct.endDate
            WHERE EXTRACT(HOUR FROM c.dateTimeIn) BETWEEN 15 AND 22
                AND ct.employee.id = c.employee.id
                AND ct.company.id = :companyId
                AND c.dateTimeIn >= :since
                AND c.dateTimeOut < CURRENT_TIMESTAMP
                AND c.employee.deletedAt IS NULL
            """)
    int countAfternoonToNight(@Param("companyId") Long companyId, @Param("since") LocalDateTime since);
}
