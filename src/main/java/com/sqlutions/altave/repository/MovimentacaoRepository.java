package com.sqlutions.altave.repository;

import com.sqlutions.altave.dto.MovimentacaoSearchDTO;
import com.sqlutions.altave.entity.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT m FROM Movimentacao m")
    Page<Movimentacao> searchMovimentacoes(MovimentacaoSearchDTO params, Pageable pageable);
}