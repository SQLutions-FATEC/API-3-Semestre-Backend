package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.dto.CompanyResponseDTO;
import com.sqlutions.altave.entity.Company;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.service.CompanyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CompanyResponseDTO getCompanies(int page, int size, String name) {
        if (page > 0) {
            page = page - 1;
        }

        List<Company> companies = companyRepository.findAll().stream()
                .filter(company -> company.getDeletedAt() == null)
                .toList();

        if (name != null && !name.trim().isEmpty()) {
            String nameFilter = name.trim().toLowerCase();
            companies = companies.stream()
                    .filter(c -> c.getCompanyName() != null &&
                            c.getCompanyName().toLowerCase().contains(nameFilter))
                    .toList();
        }

        int total = companies.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);

        List<CompanyDTO> paged = companies.subList(start, end).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return CompanyResponseDTO.builder()
                .items(paged)
                .total(total)
                .build();
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
    @Transactional
    public void deleteCompany(Long id) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        List<Contract> contracts = contractRepository.findByCompanyAndDeletedAtIsNull(company);

        for (Contract contract : contracts) {
            contract.setDeletedAt(LocalDateTime.now());
            contractRepository.save(contract);
        }

        company.setDeletedAt(LocalDateTime.now());
        companyRepository.save(company);
    }

    @Override
    public CompanyDTO getActiveCompanyById(Long id) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada ou já deletada"));
        return convertToDTO(company);
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
