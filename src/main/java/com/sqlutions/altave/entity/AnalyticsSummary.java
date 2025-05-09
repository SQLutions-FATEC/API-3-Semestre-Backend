package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "analytics_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "total_hours_worked", nullable = false)
    private BigDecimal totalHoursWorked;

    @Column(name = "absenteeism_rate", nullable = false)
    private BigDecimal absenteeismRate;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;
}
