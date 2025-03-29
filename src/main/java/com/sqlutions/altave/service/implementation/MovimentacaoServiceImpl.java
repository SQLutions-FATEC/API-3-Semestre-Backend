package com.sqlutions.altave.service.implementation;

import com.sqlutions.altave.dto.MovimentacaoDTO;
import com.sqlutions.altave.dto.MovimentacaoSearchDTO;
import com.sqlutions.altave.entity.Movimentacao;
import com.sqlutions.altave.repository.MovimentacaoRepository;
import com.sqlutions.altave.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    @Override
    public MovimentacaoDTO createMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        Movimentacao movimentacao = Movimentacao.builder()
                .dataHora(new Date())
                .sentido(movimentacaoDTO.getSentido())
                .funcionario(movimentacaoDTO.getFuncionario())
                .build();

        Movimentacao savedMovimentacao = movimentacaoRepository.save(movimentacao);

        return mapToDTO(savedMovimentacao);
    }

    @Override
    public MovimentacaoDTO getMovimentacaoById(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        assert movimentacao != null;
        return mapToDTO(movimentacao);
    }

    @Override
    public List<MovimentacaoDTO> searchMovimentacoes(MovimentacaoSearchDTO movimentacaoSearchDTO) {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date startDate = null;
        Date endDate = null;
        try {
            if (movimentacaoSearchDTO.getStartDataHora() != null) {
                startDate = dateFormat.parse(movimentacaoSearchDTO.getStartDataHora());
            }
            if (movimentacaoSearchDTO.getEndDataHora() != null) {
                endDate = dateFormat.parse(movimentacaoSearchDTO.getEndDataHora());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date finalStartDate = startDate;
        Date finalEndDate = endDate;

        return movimentacoes.stream()
                .filter(mov -> (finalStartDate == null || !mov.getDataHora().before(finalStartDate)) &&
                        (finalEndDate == null || !mov.getDataHora().after(finalEndDate)) &&
                        (movimentacaoSearchDTO.getSentido() == null || mov.getSentido().equalsIgnoreCase(movimentacaoSearchDTO.getSentido())) &&
                        (movimentacaoSearchDTO.getFuncionarioId() == null || mov.getFuncionario().getIdFuncionario().equals(movimentacaoSearchDTO.getFuncionarioId())))
                .map(mov -> new MovimentacaoDTO(mov.getDataHora().toString(), mov.getSentido(), mov.getFuncionario()))
                .collect(Collectors.toList());
    }

    @Override
    public MovimentacaoDTO updateMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO) throws ParseException {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        assert movimentacao != null;
        movimentacao.setSentido(movimentacaoDTO.getSentido());
        movimentacao.setFuncionario(movimentacaoDTO.getFuncionario());
        movimentacao.setDataHora(formatter.parse(movimentacaoDTO.getDataHora()));
        Movimentacao updatedMovimentacao = movimentacaoRepository.save(movimentacao);

        return mapToDTO(updatedMovimentacao);
    }

    @Override
    public MovimentacaoDTO deleteMovimentacao(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        assert movimentacao != null;
        movimentacaoRepository.delete(movimentacao);
        return mapToDTO(movimentacao);
    }

    private MovimentacaoDTO mapToDTO(Movimentacao movimentacao) {
        return MovimentacaoDTO.builder()
                .dataHora(movimentacao.getDataHora().toString())
                .sentido(movimentacao.getSentido())
                .funcionario(movimentacao.getFuncionario())
                .build();
    }
}