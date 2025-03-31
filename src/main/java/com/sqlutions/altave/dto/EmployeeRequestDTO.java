package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("employee_name")
    private String name;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser uma data no passado")
    @JsonProperty("employee_birth_date")
    private Date birthDate;

    @NotBlank(message = "Tipo sanguíneo é obrigatório")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Tipo sanguíneo deve ser no formato 'A+', 'O-', 'B+', etc.")
    @JsonProperty("employee_blood_type")
    private String bloodType;

    private String sex;
}

