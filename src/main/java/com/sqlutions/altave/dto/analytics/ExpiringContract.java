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

    @JsonProperty("register_number")
    private String employeeRegisterNumber;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("end_date")
    private LocalDate endDate;
}
