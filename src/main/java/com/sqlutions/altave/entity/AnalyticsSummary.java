package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Immutable
@Entity
@Subselect("SELECT * FROM analytics_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsSummary {

    @Id
    private Long employeeId;

    private BigDecimal totalHoursWorked;

    private String sex;

    @Column(name = "date_time_in")
    private LocalDateTime dateTimeIn;

    @Column(name = "date_time_out")
    private LocalDateTime dateTimeOut;
}
