package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class EmpresaController {

    @Autowired
    private EmpresaService companyService;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> getAllCompanies() {
        List<EmpresaDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getCompanyById(@PathVariable Long id) {
        EmpresaDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> createCompany(@RequestBody EmpresaDTO companyDTO) {
        EmpresaDTO createdCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> updateCompany(
            @PathVariable Long id,
            @RequestBody EmpresaDTO companyDTO) {
        EmpresaDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}