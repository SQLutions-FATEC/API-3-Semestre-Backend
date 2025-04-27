package com.sqlutions.altave.dto;

import com.sqlutions.altave.entity.Contract;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ContractResponseDTO {
    private Long contractId;
    private Long employeeId;
    private Long companyId;
    private Long roleId;
    private LocalDate startDate;
    private LocalDate endDate;

    public ContractResponseDTO(Contract contract) {
        this.contractId = contract.getContractId();
        this.employeeId = contract.getEmployee().getEmployeeId();
        this.companyId = contract.getCompany().getId();
        this.roleId = contract.getRole().getId();
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
    }
}
