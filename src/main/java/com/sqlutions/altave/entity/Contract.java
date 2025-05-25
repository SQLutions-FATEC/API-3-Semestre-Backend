package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public boolean isActive(LocalDate today) {
        if (startDate == null) return false;

        boolean started = !startDate.isAfter(today);
        boolean notEnded = endDate == null || !endDate.isBefore(today);

        return started && notEnded;
    }

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;
}

