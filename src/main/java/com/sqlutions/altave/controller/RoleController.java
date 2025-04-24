package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.EmployeeDTO;
import com.sqlutions.altave.dto.RoleDTO;
import com.sqlutions.altave.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "APIs para gerenciamento de funções")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    @Operation(summary = "Endpoint para criar função")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping
    @Operation(summary = "Endpoint para buscar todos as funções")
    public ResponseEntity<List<RoleDTO>> getRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
