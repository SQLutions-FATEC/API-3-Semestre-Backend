package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String tradeName;

    @Column(unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contract> contracts;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;
}

