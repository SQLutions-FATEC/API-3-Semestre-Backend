package com.sqlutions.altave.dto;

import java.util.List;
import lombok.Data;

@Data
public class CreateContractsRequestDTO {
    private Long employee_id;
    private List<SingleContractDTO> contracts;

    @Data
    public static class SingleContractDTO {
        private Long company_id;
        private Long role_id;
        private String datetime_start;
        private String datetime_end;
    }
}
