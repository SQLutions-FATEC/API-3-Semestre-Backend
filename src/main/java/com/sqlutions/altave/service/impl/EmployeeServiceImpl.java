package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.EmployeeRequestDTO;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        employee.setName(employeeRequestDTO.getNome());
        employee.setBirthDate(employeeRequestDTO.getDataNascimento());
        employee.setBloodType(employeeRequestDTO.getTipoSanguineo());
        employee.setSex(employeeRequestDTO.getSexo());
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
