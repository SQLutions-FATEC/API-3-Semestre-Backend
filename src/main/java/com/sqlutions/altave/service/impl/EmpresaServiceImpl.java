package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.model.Empresa;
import com.sqlutions.altave.repository.EmpresaRepository;
import com.sqlutions.altave.service.EmpresaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


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

    @Override
    @Transactional
    public Empresa editarEmpresa(Long id, EmpresaDTO empresaDTO) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o ID: " + id));

        if (!empresaExistente.getCnpj().equals(empresaDTO.getCnpj()) && empresaRepository.findByCnpj(empresaDTO.getCnpj()).isPresent()) {
            throw new RuntimeException("CNPJ já cadastrado!");
        }

        empresaExistente.setNomeFantasia(empresaDTO.getNomeFantasia());
        empresaExistente.setNomeSocial(empresaDTO.getNomeSocial());
        empresaExistente.setCnpj(empresaDTO.getCnpj());

        return empresaRepository.save(empresaExistente);
    }

    @Override
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }


}