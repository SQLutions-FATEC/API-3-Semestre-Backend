package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmpresaDTO;
import java.util.List;

public interface EmpresaService {
    List<EmpresaDTO> getAllCompanies();
    EmpresaDTO getCompanyById(Long id);
    EmpresaDTO createCompany(EmpresaDTO companyDTO);
    EmpresaDTO updateCompany(Long id, EmpresaDTO companyDTO);
    void deleteCompany(Long id);
}