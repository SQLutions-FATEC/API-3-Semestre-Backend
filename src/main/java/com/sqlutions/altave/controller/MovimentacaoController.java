package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.MovimentacaoDTO;
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
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@Tag(name = "Movimentações", description = "APIs para gerenciamento de movimentações")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping
    @Operation(summary = "Criar uma nova movimentação")
    public ResponseEntity<MovimentacaoDTO> createMovimentacao(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        MovimentacaoDTO createdMovimentacao = movimentacaoService.createMovimentacao(movimentacaoDTO);
        return ResponseEntity.ok(createdMovimentacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma movimentação por ID")
    public ResponseEntity<MovimentacaoDTO> getMovimentacaoById(@PathVariable Long id) {
        MovimentacaoDTO movimentacao = movimentacaoService.getMovimentacaoById(id);
        return ResponseEntity.ok(movimentacao);
    }

    @PostMapping("/search")
    @Operation(summary = "Obter todas as movimentações ou pesquisar com filtros")
    public ResponseEntity<List<MovimentacaoDTO>> searchMovimentacoes(@RequestBody(required = false) @Valid MovimentacaoSearchDTO movimentacaoSearchDTO) {
        if (movimentacaoSearchDTO == null) {
            movimentacaoSearchDTO = new MovimentacaoSearchDTO();
        }
        List<MovimentacaoDTO> movimentacoes = movimentacaoService.searchMovimentacoes(movimentacaoSearchDTO);
        return ResponseEntity.ok(movimentacoes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma movimentação")
    public ResponseEntity<MovimentacaoDTO> updateMovimentacao(@PathVariable Long id, @Valid @RequestBody MovimentacaoDTO movimentacaoDTO) throws ParseException {
        MovimentacaoDTO updatedMovimentacao = movimentacaoService.updateMovimentacao(id, movimentacaoDTO);
        return ResponseEntity.ok(updatedMovimentacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma movimentação")
    public ResponseEntity<MovimentacaoDTO> deleteMovimentacao(@PathVariable Long id) {
        MovimentacaoDTO deletedMovimentacao = movimentacaoService.deleteMovimentacao(id);
        return ResponseEntity.ok(deletedMovimentacao);
    }
}
