package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.model.Empresa;
import jakarta.validation.Valid;
import java.util.List;

public interface EmpresaService {
    Empresa criarEmpresa(EmpresaDTO empresaDTO);

    Empresa editarEmpresa(Long id, @Valid EmpresaDTO empresaDTO);

    List<Empresa> listarEmpresas();
}