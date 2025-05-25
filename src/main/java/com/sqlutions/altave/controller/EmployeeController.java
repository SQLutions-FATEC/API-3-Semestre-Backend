package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.ClockInResponseWithTotalDTO;
import com.sqlutions.altave.dto.EmployeeDTO;
import com.sqlutions.altave.dto.EmployeeResponseWithTotalDTO;
import com.sqlutions.altave.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employees", description = "APIs para gerenciamento de funcionários")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Endpoint para criar funcionário")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para buscar funcionário pelo ID")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint para atualizar funcionario existente")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping
    @Operation(summary = "Endpoint para listar funcionários com ou sem paginação")
    public ResponseEntity<?> getEmployees(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String name) {

        if (page == null || size == null || page < 0 || size <= 0) {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        }
        EmployeeResponseWithTotalDTO response = employeeService.getEmployees(page, size, name);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint para deletar funcionário")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @Operation(summary = "Endpoint para buscar os funcionários ativos")
    public List<EmployeeDTO> getAllActiveEmployees() {
        return employeeService.getAllActiveEmployees();
    }
}
