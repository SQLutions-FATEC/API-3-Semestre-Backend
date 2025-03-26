package com.sqlutions.altave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sqlutions.altave.model.Empresa;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCnpj(String cnpj);
}