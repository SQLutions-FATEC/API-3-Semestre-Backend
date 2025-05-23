package com.sqlutions.altave.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncompleteClockIn {
    @JsonProperty("clock_in_id")
    private Long clockInId;

    @JsonProperty("register_number")
    private String employeeRegisterNumber;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("date_time_in")
    private LocalDateTime dateTimeIn;

    @JsonProperty("date_time_out")
    private LocalDateTime dateTimeOut;
}
