package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.CompanyDTO;
import org.springframework.data.domain.Page;

public interface CompanyService {
    Page<CompanyDTO> getCompanies(int page, int size);
    CompanyDTO getCompanyById(Long id);
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    void deleteCompany(Long id);
}