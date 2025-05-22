package com.sqlutions.altave.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpiringContract {
    @JsonProperty("contract_id")
    private Long contractId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("end_date")
    private LocalDate endDate;
}
