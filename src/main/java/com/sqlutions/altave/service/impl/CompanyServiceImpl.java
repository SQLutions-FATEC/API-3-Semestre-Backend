package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.entity.ClockIn;
import com.sqlutions.altave.entity.Company;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.repository.ClockInRepository;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ClockInRepository clockInRepository;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CompanyDTO> getCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return companyRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        return convertToDTO(company);
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = convertToEntity(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        existingCompany.setCompanyName(companyDTO.getCompanyName());
        existingCompany.setTradeName(companyDTO.getTradeName());
        existingCompany.setCnpj(companyDTO.getCnpj());

        Company updatedCompany = companyRepository.save(existingCompany);
        return convertToDTO(updatedCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        List<Contract> contracts = contractRepository.findByCompany(company);

        for (Contract contract : contracts) {
            Employee employee = contract.getEmployee();
            List<ClockIn> clockIns = clockInRepository.findByEmployee(employee);

            clockInRepository.deleteAll(clockIns);

            contractRepository.delete(contract);
        }

        companyRepository.delete(company);
    }

    private CompanyDTO convertToDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getCompanyName(),
                company.getCnpj(),
                company.getTradeName()
        );
    }

    private Company convertToEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setCompanyName(companyDTO.getCompanyName());
        company.setTradeName(companyDTO.getTradeName());
        company.setCnpj(companyDTO.getCnpj());
        return company;
    }
}