package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.ContractRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;
import com.sqlutions.altave.dto.CreateContractsRequestDTO;

import java.util.List;

public interface ContractService {
    ContractResponseDTO createContract(ContractRequestDTO contractRequestDTO);
    List<ContractResponseDTO> createContracts(CreateContractsRequestDTO request);
    ContractResponseDTO updateContract(Long id, ContractRequestDTO contractRequestDTO);
    void deleteContract(Long id);
    List<ContractResponseDTO> getContractsByEmployee(Long employeeId);
    List<ContractResponseDTO> getContractsByCompany(Long companyId);
    ContractResponseDTO inactivateContract(Long contractId);
}
