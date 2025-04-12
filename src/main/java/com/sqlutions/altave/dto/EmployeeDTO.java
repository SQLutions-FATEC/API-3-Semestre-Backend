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
    @JsonProperty("id")
    private Long id;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("employee_birth_date")
    private Date birthDate;
    @JsonProperty("employee_sex")
    private String sex;
    @JsonProperty("employee_blood_type")
    private String bloodType;
}
