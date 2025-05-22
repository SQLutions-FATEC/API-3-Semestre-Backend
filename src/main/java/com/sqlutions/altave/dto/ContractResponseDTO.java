package com.sqlutions.altave.dto;

import com.sqlutions.altave.entity.Contract;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ContractResponseDTO {
    private Long id;
    private String company;
    private String role;
    private LocalDate date_start;
    private LocalDate date_end;
    private Boolean active;

    public ContractResponseDTO(Contract contract) {
        this.id = contract.getContractId();
        this.company = contract.getCompany().getCompanyName();
        this.role = contract.getRole().getName();
        this.date_start = contract.getStartDate();
        this.date_end = contract.getEndDate();
        this.active = contract.isActive(LocalDate.now());
    }
}
