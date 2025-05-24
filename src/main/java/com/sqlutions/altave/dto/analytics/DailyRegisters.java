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
public class DailyRegisters {
    @JsonProperty("clock_in_with_in_count")
    private Integer clockInWithInCount;

    @JsonProperty("clock_in_with_out_count")
    private Integer clockInWithOutCount;
}
