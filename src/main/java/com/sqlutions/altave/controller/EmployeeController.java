package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.EmployeeRequestDTO;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("employee")
@Tag(name = "Employees", description = "APIs para gerenciamento de funcionários")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Endpoint para criar funcionário")
    public ResponseEntity<Employee> criarFuncionario(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
        Employee employeeCriado = employeeService.createEmployee(employeeRequestDTO);
        return new ResponseEntity<>(employeeCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para buscar funcionário pelo ID")
    public ResponseEntity<Employee> buscarFuncionarioPorId(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Endpoint para buscar todos os funcionários")
    public ResponseEntity<List<Employee>> listarFuncionarios() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}

