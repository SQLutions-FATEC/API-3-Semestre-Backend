package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @Column(name = "name")
    private String employeeName;
    private Date birthDate;
    private String sex;
    private String bloodType;

    @OneToMany(mappedBy = "employee")
    private Set<ClockIn> clockIns;
}

