package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}

