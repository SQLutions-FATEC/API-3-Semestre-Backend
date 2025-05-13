package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();
    Page<CompanyDTO> getCompanies(int page, int size);
    CompanyDTO getCompanyById(Long id);
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    void deleteCompany(Long id);
}