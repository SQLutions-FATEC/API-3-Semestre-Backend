package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BulkContractUpdateRequestDTO {
    @JsonProperty("employee_id")
    private Long employeeId;

    private List<ContractOperationDTO> contracts;

    @Data
    public static class ContractOperationDTO {
        private Long id;

        @JsonProperty("company_id")
        private Long companyId;

        @JsonProperty("role_id")
        private Long roleId;

        @JsonProperty("date_start")
        private LocalDate startDate;

        @JsonProperty("date_end")
        private LocalDate endDate;

        private Boolean active;

        private String action;

        private transient String companyName;
        private transient String roleName;
    }
}
