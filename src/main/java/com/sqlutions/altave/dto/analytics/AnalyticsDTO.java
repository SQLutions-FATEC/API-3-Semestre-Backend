package com.sqlutions.altave.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsDTO {

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("clock_in_with_in_count")
    private int clockInWithInCount;

    @JsonProperty("clock_in_with_out_count")
    private int clockInWithOutCount;

    @JsonProperty("hours_worked_by_role")
    private List<RoleHours> hoursWorkedByRole;

    @JsonProperty("male_workers")
    private int maleWorkers;

    @JsonProperty("female_workers")
    private int femaleWorkers;

    @JsonProperty("expiring_contracts")
    private List<ExpiringContract> expiringContracts;

    @JsonProperty("incomplete_clock_ins")
    private List<IncompleteClockIn> incompleteClockIns;

    @JsonProperty("midnight_to_morning")
    private int midnightToMorning;

    @JsonProperty("morning_to_afternoon")
    private int morningToAfternoon;

    @JsonProperty("afternoon_to_night")
    private int afternoonToNight;
}
