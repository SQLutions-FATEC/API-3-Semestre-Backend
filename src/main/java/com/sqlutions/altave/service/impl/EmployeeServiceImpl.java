package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.*;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id){
        Employee employee = employeeRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado ou foi deletado"));
        return convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getAllActiveEmployees() {
        return employeeRepository.findAllActive().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado ou já deletado"));

        employee.setDeletedAt(LocalDateTime.now());
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponseWithTotalDTO getEmployees(int page, int size, String name) {
        if (page > 0) {
            page = page - 1;
        }

        List<Employee> employees = employeeRepository.findAll();

        if (name != null && !name.trim().isEmpty()) {
            String nameFilter = name.trim().toLowerCase();
            employees = employees.stream()
                    .filter(e -> e.getEmployeeName() != null &&
                            e.getEmployeeName().toLowerCase().contains(nameFilter))
                    .toList();
        }

        int total = employees.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);

        List<EmployeeDTO> paged = employees.subList(start, end).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return EmployeeResponseWithTotalDTO.builder()
                .items(paged)
                .total(total)
                .build();
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        existingEmployee.setEmployeeName(employeeDTO.getEmployeeName());
        existingEmployee.setBirthDate(employeeDTO.getBirthDate());
        existingEmployee.setSex(employeeDTO.getSex());
        existingEmployee.setBloodType(employeeDTO.getBloodType());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDTO(updatedEmployee);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getEmployeeName(),
                employee.getRegisterNumber(),
                employee.getBirthDate(),
                employee.getSex(),
                employee.getBloodType()
        );
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setRegisterNumber(employeeDTO.getRegisterNumber());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setSex(employeeDTO.getSex());
        employee.setBloodType(employeeDTO.getBloodType());
        return employee;
    }
}
