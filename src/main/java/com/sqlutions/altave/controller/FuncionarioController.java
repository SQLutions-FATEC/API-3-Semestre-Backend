package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.FuncionarioRequestDTO;
import com.sqlutions.altave.entity.Funcionario;
import com.sqlutions.altave.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("funcionarios")
@Tag(name = "Employees", description = "APIs para gerenciamento de funcionários")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    @Operation(summary = "Endpoint para criar funcionário")
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody @Valid FuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionarioCriado = funcionarioService.criarFuncionario(funcionarioRequestDTO);
        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para buscar funcionário pelo ID")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Endpoint para buscar todos os funcionários")
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }
}

