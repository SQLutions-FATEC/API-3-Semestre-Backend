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
    @JsonProperty("id_employee")
    private Long idEmployee;
    @JsonProperty("name")
    private String name;
    @JsonProperty("birth_date")
    private Date birthDate;
    @JsonProperty("blood_type")
    private String bloodType;
    @JsonProperty("sex")
    private String sex;
}
