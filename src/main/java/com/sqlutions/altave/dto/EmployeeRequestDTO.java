package com.sqlutions.altave.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser uma data no passado")
    private Date dataNascimento;

    @NotBlank(message = "Tipo sanguíneo é obrigatório")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Tipo sanguíneo deve ser no formato 'A+', 'O-', 'B+', etc.")
    private String tipoSanguineo;

    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "^([MF])$", message = "Sexo deve ser 'M' (masculino) ou 'F' (feminino)")
    private String sexo;
}

