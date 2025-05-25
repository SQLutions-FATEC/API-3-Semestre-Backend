package com.sqlutions.altave.dto;

import com.sqlutions.altave.entity.Contract;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ContractResponseDTO {
    private Long id;
    private CompanyInfo company;
    private RoleInfo role;
    private LocalDate date_start;
    private LocalDate date_end;
    private Boolean active;

    public ContractResponseDTO(Contract contract) {
        this.id = contract.getContractId();
        this.company = new CompanyInfo(
                contract.getCompany().getId(),
                contract.getCompany().getCompanyName()
        );
        this.role = new RoleInfo(
                contract.getRole().getId(),
                contract.getRole().getName()
        );
        this.date_start = contract.getStartDate();
        this.date_end = contract.getEndDate();
        this.active = contract.isActive(LocalDate.now());
    }

    @Data
    private static class CompanyInfo {
        private final Long id;
        private final String name;
    }

    @Data
    private static class RoleInfo {
        private final Long id;
        private final String name;
    }
}
