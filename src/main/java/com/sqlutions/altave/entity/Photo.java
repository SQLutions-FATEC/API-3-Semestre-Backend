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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String path;
    private LocalDateTime creationDate;
    private String originalFilename;
}
