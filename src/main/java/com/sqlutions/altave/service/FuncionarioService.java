package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.FuncionarioRequestDTO;
import com.sqlutions.altave.entity.Funcionario;
import java.util.List;
import java.util.Optional;

    public interface FuncionarioService {
        Funcionario criarFuncionario(FuncionarioRequestDTO FuncionarioRequestDTO);
        Optional<Funcionario> buscarPorId(Long id);
        List<Funcionario> listarTodos();
    }

