package com.sqlutions.altave.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContractRequestDTO {
    private Long id;

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("date_start")
    private LocalDate startDate;

    @JsonProperty("date_end")
    private LocalDate endDate;

    private transient String companyName;
    private transient String roleName;
}
