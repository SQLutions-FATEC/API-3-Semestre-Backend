package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.ContractRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;

import java.util.List;

public interface ContractService {
    ContractResponseDTO createContract(ContractRequestDTO contractRequestDTO);
    ContractResponseDTO updateContract(Long id, ContractRequestDTO contractRequestDTO);
    void deleteContract(Long id);
    List<ContractResponseDTO> getContractsByEmployee(Long employeeId);
    List<ContractResponseDTO> getContractsByCompany(Long companyId);
    ContractResponseDTO inactivateContract(Long contractId);
}
