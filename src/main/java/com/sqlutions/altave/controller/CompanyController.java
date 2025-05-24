package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.dto.CompanyResponseDTO;
import com.sqlutions.altave.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Tag(name = "Companies", description = "APIs para gerenciamento de empresas")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Operation(summary = "Buscar empresas com filtros e paginação")
    @GetMapping
    public ResponseEntity<?> getCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) boolean all,
            @RequestParam(required = false) String name) {

        if (all) {
            List<CompanyDTO> companies = companyService.getAllCompanies();
            return ResponseEntity.ok(companies);
        }

        CompanyResponseDTO response = companyService.getCompanies(page, size,name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para buscar empresa pelo ID")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    @Operation(summary = "Endpoint para criar nova empresa")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint para atualizar empresa existente")
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable Long id,
            @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint para deletar empresa")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}