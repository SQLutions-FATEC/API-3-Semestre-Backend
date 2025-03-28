package com.sqlutions.altave.service.implementation;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.entity.Empresa;
import com.sqlutions.altave.repository.EmpresaRepository;
import com.sqlutions.altave.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository companyRepository;

    @Override
    public List<EmpresaDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpresaDTO getCompanyById(Long id) {
        Empresa company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        return convertToDTO(company);
    }

    @Override
    public EmpresaDTO createCompany(EmpresaDTO companyDTO) {
        Empresa company = convertToEntity(companyDTO);
        Empresa savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

    @Override
    public EmpresaDTO updateCompany(Long id, EmpresaDTO companyDTO) {
        Empresa existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        existingCompany.setRazaoSocial(companyDTO.getCompanyName());
        existingCompany.setNomeFantasia(companyDTO.getTradeName());
        existingCompany.setCnpj(companyDTO.getCnpj());

        Empresa updatedCompany = companyRepository.save(existingCompany);
        return convertToDTO(updatedCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private EmpresaDTO convertToDTO(Empresa company) {
        return new EmpresaDTO(
                company.getIdEmpresa(),
                company.getRazaoSocial(),
                company.getCnpj(),
                company.getNomeFantasia()
        );
    }

    private Empresa convertToEntity(EmpresaDTO companyDTO) {
        Empresa company = new Empresa();
        company.setIdEmpresa(companyDTO.getId());
        company.setRazaoSocial(companyDTO.getCompanyName());
        company.setNomeFantasia(companyDTO.getTradeName());
        company.setCnpj(companyDTO.getCnpj());
        return company;
    }
}