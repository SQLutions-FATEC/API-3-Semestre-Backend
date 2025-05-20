package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.dto.CompanyResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();
    CompanyResponseDTO getCompanies(CompanyDTO companyDTO, int page, int size);
    CompanyDTO getCompanyById(Long id);
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    void deleteCompany(Long id);
}