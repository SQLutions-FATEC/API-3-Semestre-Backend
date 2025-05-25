package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.BulkContractUpdateRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;
import com.sqlutions.altave.dto.CreateContractsRequestDTO;
import com.sqlutions.altave.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contracts")
@Tag(name = "Contracts", description = "Endpoints para gerenciamento de contratos")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    @Operation(summary = "Endpoint para criar um novo contrato")
    public ResponseEntity<List<ContractResponseDTO>> createContracts(@Valid @RequestBody CreateContractsRequestDTO request) {
        return ResponseEntity.ok(contractService.createContracts(request));
    }

    @PutMapping
    @Operation(summary = "Endpoint para atualizar contratos")
    public ResponseEntity<Void> bulkUpdateContracts(
            @Valid @RequestBody BulkContractUpdateRequestDTO request) {

        contractService.processBulkUpdate(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{contractId}")
    @Operation(summary = "Endpoint para excluir um contrato")
    public ResponseEntity<Void> deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Endpoint para buscar contratos por funcionário")
    public ResponseEntity<List<ContractResponseDTO>> getContractsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(contractService.getContractsByEmployee(employeeId));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Endpoint para buscar contratos por empresa")
    public ResponseEntity<List<ContractResponseDTO>> getContractsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(contractService.getContractsByCompany(companyId));
    }

    @PutMapping("/{contractId}/inactivate")
    @Operation(summary = "Endpoint para inativar um contrato")
    public ResponseEntity<ContractResponseDTO> inactivateContract(@PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.inactivateContract(contractId));
    }
}
