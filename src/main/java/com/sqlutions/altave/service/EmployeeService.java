package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmployeeDTO;

import org.springframework.data.domain.Page;

    public interface EmployeeService {
        EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
        EmployeeDTO getEmployeeById(Long id);
        Page<EmployeeDTO> getEmployees(int page, int size);
        EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    }

