package com.sqlutions.altave.repository;

import com.sqlutions.altave.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByNome(String nome);

    Optional<Funcionario> findByTipoSanguineo(String tipoSanguineo);

    Optional<Funcionario> findBySexo(String sexo);

    Optional<Funcionario> findByDataNascimento(String dataNascimento);
}

