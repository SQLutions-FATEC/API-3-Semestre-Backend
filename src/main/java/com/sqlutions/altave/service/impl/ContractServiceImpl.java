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
        var employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow();
        var company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        var role = roleRepository.findById(dto.getRoleId()).orElseThrow();

        LocalDate today = LocalDate.now();
        var optionalActiveContract = contractRepository.findContractByEmployeeAndDate(employee, today);
        if (optionalActiveContract.isPresent()) {
            throw new IllegalArgumentException("O funcionário já possui um contrato ativo.");
        }

        List<Contract> existingContracts = contractRepository.findAll()
                .stream()
                .filter(c -> c.getEmployee().getId().equals(employee.getId()))
                .toList();

        for (Contract existing : existingContracts) {
            boolean datesOverlap = !(dto.getEndDate().isBefore(existing.getStartDate()) ||
                    dto.getStartDate().isAfter(existing.getEndDate()));
            if (datesOverlap) {
                throw new IllegalArgumentException("As datas do novo contrato estão em conflito com um contrato já existente.");
            }
        }

        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setCompany(company);
        contract.setRole(role);
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
}
