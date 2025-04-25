package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    @JsonProperty("name")
    private String employeeName;
    @JsonProperty("register_number")
    private String registerNumber;
    @JsonProperty("birth_date")
    private Date birthDate;
    @JsonProperty("gender")
    private String sex;
    @JsonProperty("blood_type")
    private String bloodType;
}
