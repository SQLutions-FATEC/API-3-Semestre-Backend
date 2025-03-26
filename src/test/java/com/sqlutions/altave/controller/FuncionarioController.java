package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.FuncionarioDTO;
import com.sqlutions.altave.model.Funcionario;
import com.sqlutions.altave.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
        Funcionario funcionarioCriado = funcionarioService.criarFuncionario(funcionarioDTO);

        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }
}

