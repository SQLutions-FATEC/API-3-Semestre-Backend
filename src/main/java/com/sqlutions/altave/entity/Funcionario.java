package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;
    private String nome;
    private Date dataNascimento;
    private String sexo;
    private String tipoSanguineo;

    @OneToMany(mappedBy = "funcionario")
    private Set<Movimentacao> movimentacoes;
}

