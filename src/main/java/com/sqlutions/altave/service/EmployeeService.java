package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmployeeRequestDTO;
import com.sqlutions.altave.entity.Employee;

import java.util.List;
import java.util.Optional;

    public interface EmployeeService {
        Employee createEmployee(EmployeeRequestDTO EmployeeRequestDTO);
        Optional<Employee> getEmployeeById(Long id);
        List<Employee> getAllEmployees();
    }

