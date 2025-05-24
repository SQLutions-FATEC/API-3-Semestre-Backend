package com.sqlutions.altave.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"clockIns", "contracts"})
@EqualsAndHashCode(exclude = {"clockIns", "contracts"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String employeeName;

    @Column(name = "register_number", nullable = false, unique = true)
    private String registerNumber;

    private Date birthDate;
    private String sex;
    private String bloodType;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Contract> contracts;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<ClockIn> clockIns;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;
}
