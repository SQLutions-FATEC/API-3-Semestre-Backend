package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.ContractRequestDTO;
import com.sqlutions.altave.dto.ContractResponseDTO;
import com.sqlutions.altave.dto.CreateContractsRequestDTO;
import com.sqlutions.altave.service.ContractService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<ContractResponseDTO>> createContracts(@RequestBody CreateContractsRequestDTO request) {
        return ResponseEntity.ok(contractService.createContracts(request));
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<ContractResponseDTO> updateContract(@PathVariable Long contractId,
                                                              @RequestBody ContractRequestDTO dto) {
        return ResponseEntity.ok(contractService.updateContract(contractId, dto));
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ContractResponseDTO>> getContractsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(contractService.getContractsByEmployee(employeeId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ContractResponseDTO>> getContractsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(contractService.getContractsByCompany(companyId));
    }

    @PutMapping("/{contractId}/inactivate")
    public ResponseEntity<ContractResponseDTO> inactivateContract(@PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.inactivateContract(contractId));
    }
}
