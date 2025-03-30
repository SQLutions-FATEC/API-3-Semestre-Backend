package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.MovimentacaoRequestDTO;
import com.sqlutions.altave.dto.MovimentacaoResponseDTO;
import com.sqlutions.altave.dto.MovimentacaoResponseWithTotalDTO;
import com.sqlutions.altave.dto.MovimentacaoSearchDTO;

import java.text.ParseException;

public interface MovimentacaoService {
    MovimentacaoResponseDTO createMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO);
    MovimentacaoResponseDTO getMovimentacaoById(Long id);
    MovimentacaoResponseWithTotalDTO searchMovimentacoes(MovimentacaoSearchDTO movimentacaoSearchDTO, int page, int size);
    MovimentacaoResponseDTO updateMovimentacao(Long id, MovimentacaoRequestDTO movimentacaoRequestDTO) throws ParseException;
    MovimentacaoResponseDTO deleteMovimentacao(Long id);
}