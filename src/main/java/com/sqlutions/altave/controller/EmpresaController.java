package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.EmpresaDTO;
import com.sqlutions.altave.model.Empresa;
import com.sqlutions.altave.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<Empresa> criarEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) {
        Empresa novaEmpresa = empresaService.criarEmpresa(empresaDTO);
        return new ResponseEntity<>(novaEmpresa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> editarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresaEditada = empresaService.editarEmpresa(id, empresaDTO);
        return new ResponseEntity<>(empresaEditada, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarEmpresas();
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }


}