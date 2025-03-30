package com.sqlutions.altave.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoResponseDTO {
    private String dataHora;
    private String sentido;
    private FuncionarioResponseDTO funcionario;
}
