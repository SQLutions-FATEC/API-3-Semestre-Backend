package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Formula("(select e.name from employee e where e.employee_id = employee_id)")
    private String employeeName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    private String path;
    private LocalDateTime creationDate;
    private String originalFilename;
}

