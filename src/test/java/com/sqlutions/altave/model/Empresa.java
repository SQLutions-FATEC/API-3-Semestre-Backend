package com.sqlutions.altave.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "nome_social", nullable = false)
    private String nomeSocial;

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;
}
