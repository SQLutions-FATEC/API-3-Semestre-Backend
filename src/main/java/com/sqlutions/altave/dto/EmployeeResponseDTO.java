package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    @JsonProperty("id_funcionario")
    private Long idFuncionario;
    private String nome;
    private Date dataNascimento;
    private String tipoSanguineo;
    private String sexo;
}
