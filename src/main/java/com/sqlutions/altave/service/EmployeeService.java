package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.EmployeeDTO;
import java.util.List;

import com.sqlutions.altave.dto.EmployeeResponseWithTotalDTO;
import org.springframework.data.domain.Page;

    public interface EmployeeService {
        EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
        EmployeeDTO getEmployeeById(Long id);
        EmployeeResponseWithTotalDTO getEmployees(int page, int size);
        List<EmployeeDTO> getAllEmployees();
        EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
        void deleteEmployee(Long id);
        List<EmployeeDTO> getAllActiveEmployees();
    }

