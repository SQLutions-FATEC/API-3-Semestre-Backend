package com.sqlutions.altave.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContractRequestDTO {
    @JsonProperty("id")
    private Long employee_id;

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("datetime_start")
    private LocalDate startDate;

    @JsonProperty("datetime_end")
    private LocalDate endDate;
}
