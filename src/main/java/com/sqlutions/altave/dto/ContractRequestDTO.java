package com.sqlutions.altave.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ContractRequestDTO {
    private Long employee_id;
    private Long companyId;
    private Long roleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
