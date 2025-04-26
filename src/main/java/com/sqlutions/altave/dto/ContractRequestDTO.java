package com.sqlutions.altave.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ContractRequestDTO {
    private Long employeeId;
    private Long companyId;
    private Long roleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
