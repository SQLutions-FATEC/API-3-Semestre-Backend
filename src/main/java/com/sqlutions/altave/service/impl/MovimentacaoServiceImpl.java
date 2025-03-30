package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.EmpresaListDTO;
import com.sqlutions.altave.dto.FuncionarioListDTO;
import com.sqlutions.altave.dto.FuncionarioResponseDTO;
import com.sqlutions.altave.dto.MovimentacaoListDTO;
import com.sqlutions.altave.dto.MovimentacaoRequestDTO;
import com.sqlutions.altave.dto.MovimentacaoResponseDTO;
import com.sqlutions.altave.dto.MovimentacaoResponseWithTotalDTO;
import com.sqlutions.altave.dto.MovimentacaoSearchDTO;
import com.sqlutions.altave.entity.Funcionario;
import com.sqlutions.altave.entity.Movimentacao;
import com.sqlutions.altave.repository.MovimentacaoRepository;
import com.sqlutions.altave.service.FuncionarioService;
import com.sqlutions.altave.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    private final FuncionarioService funcionarioService;

    public MovimentacaoServiceImpl(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Override
    public MovimentacaoResponseDTO createMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO) {
        Movimentacao movimentacao = Movimentacao.builder()
                .dataHora(LocalDateTime.now())
                .sentido(movimentacaoRequestDTO.getSentido())
                .funcionario(funcionarioService.buscarPorId(movimentacaoRequestDTO.getFuncionario()).orElse(null))
                .build();

        Movimentacao savedMovimentacao = movimentacaoRepository.save(movimentacao);

        return mapToDTO(savedMovimentacao);
    }

    @Override
    public MovimentacaoResponseDTO getMovimentacaoById(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        assert movimentacao != null;
        return mapToDTO(movimentacao);
    }

    @Override
    public MovimentacaoResponseWithTotalDTO searchMovimentacoes(MovimentacaoSearchDTO movimentacaoSearchDTO, int page, int size) {
        if (page > 0) {
            page = page - 1;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Movimentacao> movimentacoesPage = movimentacaoRepository.findAll(pageable);

        List<MovimentacaoListDTO> movimentacoes = movimentacoesPage.stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());

        return MovimentacaoResponseWithTotalDTO.builder()
                .items(movimentacoes)
                .total(movimentacoesPage.getTotalElements())
                .build();
    }

    @Override
    public MovimentacaoResponseDTO updateMovimentacao(Long id, MovimentacaoRequestDTO movimentacaoRequestDTO) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        assert movimentacao != null;
        movimentacao.setSentido(movimentacaoRequestDTO.getSentido());
        movimentacao.setFuncionario(funcionarioService.buscarPorId(movimentacaoRequestDTO.getFuncionario()).orElse(null));
        movimentacao.setDataHora(LocalDateTime.parse(movimentacaoRequestDTO.getDataHora(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Movimentacao updatedMovimentacao = movimentacaoRepository.save(movimentacao);

        return mapToDTO(updatedMovimentacao);
    }

    @Override
    public MovimentacaoResponseDTO deleteMovimentacao(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        assert movimentacao != null;
        movimentacaoRepository.delete(movimentacao);
        return mapToDTO(movimentacao);
    }

    private MovimentacaoResponseDTO mapToDTO(Movimentacao movimentacao) {
        return MovimentacaoResponseDTO.builder()
                .dataHora(movimentacao.getDataHora().toString())
                .sentido(movimentacao.getSentido())
                .funcionario(mapToFuncionarioDTO(movimentacao.getFuncionario()))
                .build();
    }

    private MovimentacaoListDTO mapToListDTO(Movimentacao movimentacao) {
        return MovimentacaoListDTO.builder()
                .id(movimentacao.getIdMovimentacao())
                .funcionario(mapToFuncionarioListDTO(movimentacao.getFuncionario()))
                .empresa(new EmpresaListDTO(123L, "Empresa"))
                .nomeFuncao("Função")
                .sentido(movimentacao.getSentido())
                .dataHora(movimentacao.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    private FuncionarioResponseDTO mapToFuncionarioDTO(Funcionario funcionario) {
        return FuncionarioResponseDTO.builder()
                .idFuncionario(funcionario.getIdFuncionario())
                .nome(funcionario.getNome())
                .tipoSanguineo(funcionario.getTipoSanguineo())
                .sexo(funcionario.getSexo())
                .dataNascimento(funcionario.getDataNascimento())
                .build();
    }

    private FuncionarioListDTO mapToFuncionarioListDTO(Funcionario funcionario) {
        return FuncionarioListDTO.builder()
                .idFuncionario(funcionario.getIdFuncionario())
                .nome(funcionario.getNome())
                .build();
    }
}