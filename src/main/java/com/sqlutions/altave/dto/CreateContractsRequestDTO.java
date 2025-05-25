package com.sqlutions.altave.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateContractsRequestDTO {
    @JsonProperty("employee_id")
    private Long employeeId;
    private List<SingleContractDTO> contracts;

    @Data
    public static class SingleContractDTO {
        @JsonProperty("company_id")
        private Long companyId;

        @JsonProperty("role_id")
        private Long roleId;

        @JsonProperty("date_start")
        private String dateStart;

        @JsonProperty("date_end")
        private String dateEnd;
    }
}
