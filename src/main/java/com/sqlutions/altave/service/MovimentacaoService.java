package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.MovimentacaoDTO;
import com.sqlutions.altave.dto.MovimentacaoSearchDTO;

import java.text.ParseException;
import java.util.List;

public interface MovimentacaoService {
    MovimentacaoDTO createMovimentacao(MovimentacaoDTO movimentacaoDTO);
    MovimentacaoDTO getMovimentacaoById(Long id);
    List<MovimentacaoDTO> searchMovimentacoes(MovimentacaoSearchDTO movimentacaoSearchDTO);
    MovimentacaoDTO updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO) throws ParseException;
    MovimentacaoDTO deleteMovimentacao(Long id);
}