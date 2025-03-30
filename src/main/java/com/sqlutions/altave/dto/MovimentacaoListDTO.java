package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoListDTO {
    @JsonProperty("id")
    private Long id;
    /*@JsonProperty("register_number")
    private Long numeroRegistro;*/
    @JsonProperty("employee")
    private FuncionarioListDTO funcionario;
    @JsonProperty("company")
    private EmpresaListDTO empresa;
    @JsonProperty("role_name")
    private String nomeFuncao;
    @JsonProperty("direction")
    private String sentido;
    @JsonProperty("date_time")
    private String dataHora;
}
