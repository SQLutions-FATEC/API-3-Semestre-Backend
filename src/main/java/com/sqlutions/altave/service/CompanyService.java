package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.dto.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();
    CompanyResponseDTO getCompanies(int page, int size, String name);
    CompanyDTO getCompanyById(Long id);
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    void deleteCompany(Long id);
    CompanyDTO getActiveCompanyById(Long id);
}