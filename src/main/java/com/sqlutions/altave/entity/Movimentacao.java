package com.sqlutions.altave.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;
    private Date dataHora;
    private String sentido;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

}