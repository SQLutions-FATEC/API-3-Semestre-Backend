package com.sqlutions.altave.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoSearchDTO {

    private Long funcionario;
    private Long empresa;
    private Long funcao;
    private LocalDateTime startedAtDate;
    private LocalDateTime endAtDate;
}
