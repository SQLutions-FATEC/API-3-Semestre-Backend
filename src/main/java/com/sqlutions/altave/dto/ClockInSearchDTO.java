package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClockInSearchDTO {
    @JsonProperty("employee")
    private String employee;
    @JsonProperty("company")
    private String company;
    @JsonProperty("role")
    private String role;
    @JsonProperty("started_at_date")
    private LocalDateTime startedAtDate;
    @JsonProperty("end_at_date")
    private LocalDateTime endAtDate;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("min_hours")
    private Double minHours;
    @JsonProperty("max_hours")
    private Double maxHours;
}
