package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.FuncionarioRequestDTO;
import com.sqlutions.altave.entity.Funcionario;
import com.sqlutions.altave.repository.FuncionarioRepository;
import com.sqlutions.altave.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario criarFuncionario(FuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioRequestDTO.getNome());
        funcionario.setDataNascimento(funcionarioRequestDTO.getDataNascimento());
        funcionario.setTipoSanguineo(funcionarioRequestDTO.getTipoSanguineo());
        funcionario.setSexo(funcionarioRequestDTO.getSexo());
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }
}
