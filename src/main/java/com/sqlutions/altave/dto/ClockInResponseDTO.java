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
public class ClockInResponseDTO {
    @JsonProperty("date_time")
    private String dateTime;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("employee")
    private EmployeeResponseDTO employee;
}
