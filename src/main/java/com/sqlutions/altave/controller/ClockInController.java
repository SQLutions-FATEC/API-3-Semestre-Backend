package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.ClockInRequestDTO;
import com.sqlutions.altave.dto.ClockInResponseDTO;
import com.sqlutions.altave.dto.ClockInResponseWithTotalDTO;
import com.sqlutions.altave.dto.ClockInSearchDTO;
import com.sqlutions.altave.dto.ClockInTimeUpdateDTO;
import com.sqlutions.altave.service.ClockInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/clock_in")
@Tag(name = "Clock ins", description = "APIs para gerenciamento de movimentações")
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
    public ResponseEntity<?> searchMovimentacoes(
            @RequestParam(required = false) String employee,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(required = false) String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startedAtDate = null;
        LocalDateTime endAtDate = null;

        try {
            if (start_date != null) {
                startedAtDate = LocalDateTime.parse(start_date, formatter);
            }
            if (end_date != null) {
                endAtDate = LocalDateTime.parse(end_date, formatter);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Formato de data inválido. Use o padrão yyyy-MM-dd HH:mm");
        }

        ClockInResponseWithTotalDTO response = clockInService.searchClockIns(ClockInSearchDTO.builder()
                .employee(employee)
                .company(company)
                .role(role)
                .startedAtDate(startedAtDate)
                .endAtDate(endAtDate)
                .direction(direction)
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

    @PatchMapping("/clock_in/{id}")
    @Operation(summary = "Endpoint para atualizar o horário de uma movimentação")
    public ResponseEntity<ClockInResponseDTO> updateClockInDatetime(@PathVariable Long id, @RequestBody ClockInTimeUpdateDTO dto) {
        ClockInResponseDTO updated = clockInService.updateClockInDatetime(id, dto);
        return ResponseEntity.ok(updated);
    }
}
