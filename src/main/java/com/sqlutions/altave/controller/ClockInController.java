package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.ClockInRequestDTO;
import com.sqlutions.altave.dto.ClockInResponseDTO;
import com.sqlutions.altave.dto.ClockInResponseWithTotalDTO;
import com.sqlutions.altave.dto.ClockInSearchDTO;
import com.sqlutions.altave.service.ClockInService;
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
public class ClockInController {
    @Autowired
    private ClockInService clockInService;

    @PostMapping
    @Operation(summary = "Endpoint para criar uma nova movimentação")
    public ResponseEntity<ClockInResponseDTO> createMovimentacao(@Valid @RequestBody ClockInRequestDTO clockInRequestDTO) {
        ClockInResponseDTO createdMovimentacao = clockInService.createClockIn(clockInRequestDTO);
        return ResponseEntity.ok(createdMovimentacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para buscar uma movimentação pelo ID")
    public ResponseEntity<ClockInResponseDTO> getMovimentacaoById(@PathVariable Long id) {
        ClockInResponseDTO movimentacao = clockInService.getClockInById(id);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/search")
    @Operation(summary = "Endpoint para obter todas as movimentações ou pesquisar com filtros")
    public ResponseEntity<ClockInResponseWithTotalDTO> searchMovimentacoes(
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

        ClockInResponseWithTotalDTO response = clockInService.searchClockIns(ClockInSearchDTO.builder()
                .funcionario(funcionario)
                .empresa(empresa)
                .funcao(funcao)
                .startedAtDate(startedAtDate)
                .endAtDate(endAtDate)
                .build(), page, size);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint para atualizar uma movimentação")
    public ResponseEntity<ClockInResponseDTO> updateMovimentacao(@PathVariable Long id, @Valid @RequestBody ClockInRequestDTO clockInRequestDTO) throws ParseException {
        ClockInResponseDTO updatedMovimentacao = clockInService.updateClockIn(id, clockInRequestDTO);
        return ResponseEntity.ok(updatedMovimentacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint para excluir uma movimentação")
    public ResponseEntity<ClockInResponseDTO> deleteMovimentacao(@PathVariable Long id) {
        ClockInResponseDTO deletedMovimentacao = clockInService.deleteClockIn(id);
        return ResponseEntity.ok(deletedMovimentacao);
    }
}
