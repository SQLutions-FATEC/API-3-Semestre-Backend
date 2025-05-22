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
public class EmployeesByPeriod {
    @JsonProperty("midnight_to_morning")
    private int midnightToMorning;

    @JsonProperty("morning_to_afternoon")
    private int morningToAfternoon;

    @JsonProperty("afternoon_to_night")
    private int afternoonToNight;
}
