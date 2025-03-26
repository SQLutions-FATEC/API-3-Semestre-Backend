package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.FuncionarioDTO;
import com.sqlutions.altave.model.Funcionario;
import com.sqlutions.altave.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario criarFuncionario(FuncionarioDTO funcionarioDTO) {

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setDataNascimento(funcionarioDTO.getDataNascimento());
        funcionario.setTipoSanguineo(funcionarioDTO.getTipoSanguineo());
        funcionario.setSexo(funcionarioDTO.getSexo());

        return funcionarioRepository.save(funcionario);
    }
}
