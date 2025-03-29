package com.sqlutions.altave.dto;

import lombok.Data;

@Data
public class MovimentacaoSearchDTO {
    private String startDataHora;
    private String endDataHora;
    private String sentido;
    private Long funcionarioId;
}