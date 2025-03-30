package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Tag(name = "Companies", description = "APIs para gerenciamento de empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService companyService;

    @GetMapping
    @Operation(summary = "Endpoint para listar todas as empresas")
    public ResponseEntity<List<EmpresaDTO>> getAllCompanies() {
        List<EmpresaDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para buscar empresa pelo ID")
    public ResponseEntity<EmpresaDTO> getCompanyById(@PathVariable Long id) {
        EmpresaDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    @Operation(summary = "Endpoint para criar nova empresa")
    public ResponseEntity<EmpresaDTO> createCompany(@RequestBody EmpresaDTO companyDTO) {
        EmpresaDTO createdCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint para atualizar empresa existente")
    public ResponseEntity<EmpresaDTO> updateCompany(
            @PathVariable Long id,
            @RequestBody EmpresaDTO companyDTO) {
        EmpresaDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint para deletar todas as empresas")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}