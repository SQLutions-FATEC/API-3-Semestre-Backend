package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.model.Empresa;
import com.sqlutions.altave.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    @Transactional
    public Empresa criarEmpresa(EmpresaDTO empresaDTO) {
        if (empresaRepository.findByCnpj(empresaDTO.getCnpj()).isPresent()) {
            throw new RuntimeException("CNPJ já cadastrado!");
        }

        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(empresaDTO.getNomeFantasia());
        empresa.setNomeSocial(empresaDTO.getNomeSocial());
        empresa.setCnpj(empresaDTO.getCnpj());

        return empresaRepository.save(empresa);
    }
}
