package com.sqlutions.altave.service.implementation;

import com.sqlutions.altave.dto.FuncionarioDTO;
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
    public Funcionario criarFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setDataNascimento(funcionarioDTO.getDataNascimento());
        funcionario.setTipoSanguineo(funcionarioDTO.getTipoSanguineo());
        funcionario.setSexo(funcionarioDTO.getSexo());
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
