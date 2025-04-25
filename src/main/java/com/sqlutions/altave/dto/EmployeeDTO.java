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
    private Long employeeId;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("register_number")
    private String registerNumber;
    @JsonProperty("employee_birthDate")
    private Date birthDate;
    @JsonProperty("employee_sex")
    private String sex;
    @JsonProperty("employee_bloodType")
    private String bloodType;
}
