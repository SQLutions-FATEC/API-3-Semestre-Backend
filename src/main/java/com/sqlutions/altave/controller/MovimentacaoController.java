package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.MovimentacaoRequestDTO;
import com.sqlutions.altave.dto.MovimentacaoResponseDTO;
import com.sqlutions.altave.dto.MovimentacaoResponseWithTotalDTO;
import com.sqlutions.altave.dto.MovimentacaoSearchDTO;
import com.sqlutions.altave.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/movimentacoes")
@Tag(name = "Movimentações", description = "APIs para gerenciamento de movimentações")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping
    @Operation(summary = "Criar uma nova movimentação")
    public ResponseEntity<MovimentacaoResponseDTO> createMovimentacao(@Valid @RequestBody MovimentacaoRequestDTO movimentacaoRequestDTO) {
        MovimentacaoResponseDTO createdMovimentacao = movimentacaoService.createMovimentacao(movimentacaoRequestDTO);
        return ResponseEntity.ok(createdMovimentacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma movimentação por ID")
    public ResponseEntity<MovimentacaoResponseDTO> getMovimentacaoById(@PathVariable Long id) {
        MovimentacaoResponseDTO movimentacao = movimentacaoService.getMovimentacaoById(id);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/search")
    @Operation(summary = "Obter todas as movimentações ou pesquisar com filtros")
    public ResponseEntity<MovimentacaoResponseWithTotalDTO> searchMovimentacoes(
            @RequestParam(required = false) Long funcionario,
            @RequestParam(required = false) Long empresa,
            @RequestParam(required = false) Long funcao,
            @RequestParam(value = "started_at", required = false) String startedAt,
            @RequestParam(value = "end_at", required = false) String endAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm");

        LocalDateTime startedAtDate = null;
        LocalDateTime endAtDate = null;

        if (startedAt != null) { startedAtDate = LocalDateTime.parse(startedAt, formatter); }
        if (endAt != null) { endAtDate = LocalDateTime.parse(endAt, formatter); }

        MovimentacaoResponseWithTotalDTO response = movimentacaoService.searchMovimentacoes(MovimentacaoSearchDTO.builder()
                .funcionario(funcionario)
                .empresa(empresa)
                .funcao(funcao)
                .startedAtDate(startedAtDate)
                .endAtDate(endAtDate)
                .build(), page, size);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma movimentação")
    public ResponseEntity<MovimentacaoResponseDTO> updateMovimentacao(@PathVariable Long id, @Valid @RequestBody MovimentacaoRequestDTO movimentacaoRequestDTO) throws ParseException {
        MovimentacaoResponseDTO updatedMovimentacao = movimentacaoService.updateMovimentacao(id, movimentacaoRequestDTO);
        return ResponseEntity.ok(updatedMovimentacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma movimentação")
    public ResponseEntity<MovimentacaoResponseDTO> deleteMovimentacao(@PathVariable Long id) {
        MovimentacaoResponseDTO deletedMovimentacao = movimentacaoService.deleteMovimentacao(id);
        return ResponseEntity.ok(deletedMovimentacao);
    }
}
