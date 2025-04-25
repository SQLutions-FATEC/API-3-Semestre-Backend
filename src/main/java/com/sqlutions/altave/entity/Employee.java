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

    @Column(name = "name", nullable = false)
    private String employeeName;

    @Column(name = "register_number", nullable = false, unique = true)
    private String registerNumber;

    private Date birthDate;
    private String sex;
    private String bloodType;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "employee")
    private Set<ClockIn> clockIns;

}
