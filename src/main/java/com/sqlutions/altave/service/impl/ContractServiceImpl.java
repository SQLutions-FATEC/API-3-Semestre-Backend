package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.BulkContractUpdateRequestDTO;
import com.sqlutions.altave.dto.ContractRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;
import com.sqlutions.altave.dto.CreateContractsRequestDTO;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.exception.BusinessException;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.repository.RoleRepository;
import com.sqlutions.altave.service.ContractService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Comparator;
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

        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setCompany(company);
        contract.setRole(role);
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        return new ContractResponseDTO(contractRepository.save(contract));
    }

    @Override
    public List<ContractResponseDTO> createContracts(CreateContractsRequestDTO request) {
        var employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException("Funcionário não encontrado"));

        List<ContractResponseDTO> createdContracts = new ArrayList<>();
        boolean hasActiveContract = false;

        for (CreateContractsRequestDTO.SingleContractDTO dto : request.getContracts()) {
            LocalDate startDate = LocalDate.parse(dto.getDateStart());
            LocalDate endDate = dto.getDateEnd() != null ? LocalDate.parse(dto.getDateEnd()) : null;

            validateContractDates(startDate, endDate);

            if (endDate == null || endDate.isAfter(LocalDate.now())) {
                if (hasActiveContract) {
                    throw new BusinessException("Apenas um contrato pode estar ativo por funcionário");
                }
                hasActiveContract = true;
            }
        }

        if (hasActiveContract) {
            inactivateActiveContracts(employee.getId());
        }

        for (CreateContractsRequestDTO.SingleContractDTO dto : request.getContracts()) {
            LocalDate startDate = LocalDate.parse(dto.getDateStart());
            LocalDate endDate = dto.getDateEnd() != null ? LocalDate.parse(dto.getDateEnd()) : null;

            var company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new BusinessException("Empresa não encontrada"));
            var role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new BusinessException("Cargo não encontrado"));

            Contract contract = new Contract();
            contract.setEmployee(employee);
            contract.setCompany(company);
            contract.setRole(role);
            contract.setStartDate(startDate);
            contract.setEndDate(endDate);

            createdContracts.add(new ContractResponseDTO(contractRepository.save(contract)));
        }

        return createdContracts;
    }

    private void inactivateActiveContracts(Long employeeId) {
        List<Contract> activeContracts = contractRepository.findActiveContractsByEmployee(employeeId, LocalDate.now());
        activeContracts.forEach(contract -> {
            contract.setEndDate(LocalDate.now().minusDays(1));
            contractRepository.save(contract);
        });
    }

    @Override
    @Transactional
    public void processBulkUpdate(BulkContractUpdateRequestDTO request) {
        var employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException("Funcionário não encontrado"));

        for (BulkContractUpdateRequestDTO.ContractOperationDTO dto : request.getContracts()) {
            switch (dto.getAction().toLowerCase()) {
                case "create":
                    createNewContract(employee, dto);
                    break;
                case "update":
                    updateExistingContract(dto);
                    break;
                case "delete":
                    deleteContract(dto.getId());
                    break;
                case "keep":
                    break;
                default:
                    throw new BusinessException("Ação inválida para contrato: " + dto.getAction());
            }
        }

        validateSingleActiveContract(employee.getId());
    }

    private void createNewContract(Employee employee, BulkContractUpdateRequestDTO.ContractOperationDTO dto) {
        validateContractDates(dto.getStartDate(), dto.getEndDate());

        var company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new BusinessException("Empresa não encontrada"));
        var role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new BusinessException("Cargo não encontrado"));

        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setCompany(company);
        contract.setRole(role);
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        contractRepository.save(contract);
    }

    private void updateExistingContract(BulkContractUpdateRequestDTO.ContractOperationDTO dto) {
        Contract contract = contractRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("Contrato não encontrado para atualização"));

        validateContractDates(dto.getStartDate(), dto.getEndDate());

        var company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new BusinessException("Empresa não encontrada"));
        var role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new BusinessException("Cargo não encontrado"));

        contract.setCompany(company);
        contract.setRole(role);
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        contractRepository.save(contract);
    }

    private void validateSingleActiveContract(Long employeeId) {
        List<Contract> activeContracts = contractRepository.findActiveContractsByEmployee(employeeId, LocalDate.now());

        if (activeContracts.size() > 1) {
            Contract mostRecent = activeContracts.stream()
                    .max(Comparator.comparing(Contract::getStartDate))
                    .orElseThrow();

            activeContracts.forEach(contract -> {
                if (!contract.equals(mostRecent)) {
                    contract.setEndDate(mostRecent.getStartDate().minusDays(1));
                    contractRepository.save(contract);
                }
            });
        }
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

        contract.setEndDate(today.minusDays(1));

        return new ContractResponseDTO(contractRepository.save(contract));
    }

    private void validateContractDates(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new BusinessException("A data de início não pode ser posterior à data de término.");
        }
    }
}
