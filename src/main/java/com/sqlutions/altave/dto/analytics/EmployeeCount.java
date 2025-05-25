package com.sqlutions.altave.dto.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCount {
    @JsonProperty("male_workers")
    private int maleWorkers;

    @JsonProperty("female_workers")
    private int femaleWorkers;
}
