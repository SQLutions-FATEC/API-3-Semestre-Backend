package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private LocalDateTime dateTime;
    private String direction;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

