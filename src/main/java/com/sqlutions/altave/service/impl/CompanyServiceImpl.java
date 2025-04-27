package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.entity.Company;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

        company.setDeleted(true);  // Realizando o Soft Delete
        companyRepository.save(company);
    }

    @Override
    public List<CompanyDTO> getAllActiveCompanies() {
        List<Company> activeCompanies = companyRepository.findByDeletedFalse();  // Busca empresas ativas
        return activeCompanies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO getActiveCompanyById(Long id) {
        Company company = companyRepository.findByIdAndDeletedFalse(id)
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
