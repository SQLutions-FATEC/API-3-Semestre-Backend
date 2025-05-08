package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "employee")
@EqualsAndHashCode(exclude = "employee")
public class ClockIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clockInId;
    private LocalDateTime dateTimeIn;
    private LocalDateTime dateTimeOut;
    private String direction;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Transient
    public Double getWorkedHours() {
        if (dateTimeIn == null || dateTimeOut == null) {
            return 0.0;
        }
        long minutes = Duration.between(dateTimeIn, dateTimeOut).toMinutes();
        return minutes / 60.0;
    }
}


