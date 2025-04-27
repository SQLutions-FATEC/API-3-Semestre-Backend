package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.ContractRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.repository.RoleRepository;
import com.sqlutions.altave.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                           EmployeeRepository employeeRepository,
                           CompanyRepository companyRepository,
                           RoleRepository roleRepository) {
        this.contractRepository = contractRepository;
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
    }

    public ContractResponseDTO createContract(ContractRequestDTO dto) {
        Contract contract = new Contract();
        contract.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElseThrow());
        contract.setCompany(companyRepository.findById(dto.getCompanyId()).orElseThrow());
        contract.setRole(roleRepository.findById(dto.getRoleId()).orElseThrow());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        return new ContractResponseDTO(contractRepository.save(contract));
    }

    public ContractResponseDTO updateContract(Long contractId, ContractRequestDTO dto) {
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        contract.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElseThrow());
        contract.setCompany(companyRepository.findById(dto.getCompanyId()).orElseThrow());
        contract.setRole(roleRepository.findById(dto.getRoleId()).orElseThrow());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        return new ContractResponseDTO(contractRepository.save(contract));
    }

    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    public List<ContractResponseDTO> getContractsByEmployee(Long employeeId) {
        return contractRepository.findAll().stream()
                .filter(c -> c.getEmployee().getEmployeeId().equals(employeeId))
                .map(ContractResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ContractResponseDTO> getContractsByCompany(Long companyId) {
        return contractRepository.findAll().stream()
                .filter(c -> c.getCompany().getId().equals(companyId))
                .map(ContractResponseDTO::new)
                .collect(Collectors.toList());
    }
}
