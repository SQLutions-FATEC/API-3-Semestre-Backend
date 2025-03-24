package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.model.Empresa;

public interface EmpresaService {
    Empresa criarEmpresa(EmpresaDTO empresaDTO);
}
