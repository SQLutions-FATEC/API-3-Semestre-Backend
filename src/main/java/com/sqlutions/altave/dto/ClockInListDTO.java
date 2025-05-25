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
public class ClockInListDTO {
    private Long id;
    @JsonProperty("employee")
    private EmployeeListDTO employee;
    @JsonProperty("company")
    private CompanyListDTO company;
    @JsonProperty("role_name")
    private String roleName;
    @JsonProperty("date_time_in")
    private String dateTimeIn;
    @JsonProperty("date_time_out")
    private String dateTimeOut;
    @JsonProperty("worked_hours")
    private Double workedHours;
}

