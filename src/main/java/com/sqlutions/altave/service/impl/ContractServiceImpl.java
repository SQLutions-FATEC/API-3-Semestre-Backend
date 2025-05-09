package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.ContractRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.exception.BusinessException;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.repository.RoleRepository;
import com.sqlutions.altave.service.ContractService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

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

    @Override
    public ContractResponseDTO createContract(ContractRequestDTO dto) {
        validateContractDates(dto.getStartDate(), dto.getEndDate());

        var employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow();
        var company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        var role = roleRepository.findById(dto.getRoleId()).orElseThrow();

        if (contractRepository.findContractByEmployeeAndDate(employee, LocalDate.now()).isPresent()) {
            throw new BusinessException("O funcionário já possui um contrato ativo.");
        }

        List<Contract> overlappingContracts = contractRepository
                .findOverlappingContractsByEmployee(employee, dto.getStartDate(), dto.getEndDate());

        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setCompany(company);
        contract.setRole(role);
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        return new ContractResponseDTO(contractRepository.save(contract));
    }

    @Override
    public ContractResponseDTO updateContract(Long contractId, ContractRequestDTO dto) {
        validateContractDates(dto.getStartDate(), dto.getEndDate());
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new BusinessException("Contrato não encontrado."));

        var employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow();
        var company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        var role = roleRepository.findById(dto.getRoleId()).orElseThrow();

        contract.setEmployee(employee);
        contract.setCompany(company);
        contract.setRole(role);
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        return new ContractResponseDTO(contractRepository.save(contract));
    }


    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    public List<ContractResponseDTO> getContractsByEmployee(Long employeeId) {
        return contractRepository.findAll().stream()
                .filter(c -> c.getEmployee().getId().equals(employeeId))
                .map(ContractResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ContractResponseDTO> getContractsByCompany(Long companyId) {
        return contractRepository.findAll().stream()
                .filter(c -> c.getCompany().getId().equals(companyId))
                .map(ContractResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ContractResponseDTO inactivateContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new BusinessException("Contrato não encontrado."));

        LocalDate today = LocalDate.now();

        if (!contract.isActive(today)) {
            throw new BusinessException("Este contrato já se encontra inativo.");
        }

        if (contract.getStartDate().isAfter(today)) {
            throw new BusinessException("Não é possível inativar um contrato que ainda não começou.");
        }

        contract.setEndDate(today);

        return new ContractResponseDTO(contractRepository.save(contract));
    }

    private void validateContractDates(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new BusinessException("A data de início não pode ser posterior à data de término.");
        }
    }
}
