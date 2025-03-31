package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmployeeDTO;

import java.util.List;

    public interface EmployeeService {
        EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
        EmployeeDTO getEmployeeById(Long id);
        List<EmployeeDTO> getAllEmployees();
        EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    }

