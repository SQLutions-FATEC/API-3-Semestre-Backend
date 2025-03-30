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
public class ClockInRequestDTO {
    @JsonProperty("data_hora")
    private String dataHora;

    @JsonProperty("sentido")
    private String sentido;

    @JsonProperty("funcionario")
    private Long funcionario;
}
