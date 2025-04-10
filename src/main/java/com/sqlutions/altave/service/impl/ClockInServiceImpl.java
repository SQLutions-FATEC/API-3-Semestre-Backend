package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.*;
import com.sqlutions.altave.entity.ClockIn;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.repository.ClockInRepository;
import com.sqlutions.altave.service.EmployeeService;
import com.sqlutions.altave.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClockInServiceImpl implements ClockInService {
    @Autowired
    private ClockInRepository clockInRepository;
    private final EmployeeService employeeService;

    public ClockInServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ClockInResponseDTO createClockIn(ClockInRequestDTO clockInRequestDTO) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(clockInRequestDTO.getFuncionario());
        Employee employee = convertToEntity(employeeDTO);
        ClockIn clockIn = ClockIn.builder()
                .dateTime(LocalDateTime.now())
                .direction(clockInRequestDTO.getSentido())
                .employee(employee)
                .build();

        ClockIn savedClockIn = clockInRepository.save(clockIn);

        return mapToDTO(savedClockIn);
    }

    @Override
    public ClockInResponseDTO getClockInById(Long id) {
        ClockIn clockIn = clockInRepository.findById(id).orElse(null);
        assert clockIn != null;
        return mapToDTO(clockIn);
    }

    @Override
    public ClockInResponseWithTotalDTO searchClockIns(ClockInSearchDTO clockInSearchDTO, int page, int size) {
        if (page > 0) {
            page = page - 1;
        }

        List<ClockIn> allClockIns = clockInRepository.findAll();

        List<ClockIn> filtered = allClockIns.stream()
                .filter(ci -> clockInSearchDTO.getFuncionario() == null ||
                        ci.getEmployee().getEmployeeId().equals(clockInSearchDTO.getFuncionario()))
                .filter(ci -> clockInSearchDTO.getEmpresa() == null ||
                        (ci.getEmployee().getCompany() != null &&
                                ci.getEmployee().getCompany().getCompanyId().equals(clockInSearchDTO.getEmpresa())))
                .filter(ci -> clockInSearchDTO.getFuncao() == null ||
                        (ci.getEmployee().getRole() != null &&
                                ci.getEmployee().getRole().getRoleId().equals(clockInSearchDTO.getFuncao())))
                .filter(ci -> clockInSearchDTO.getStartedAtDate() == null ||
                        !ci.getDateTime().isBefore(clockInSearchDTO.getStartedAtDate()))
                .filter(ci -> clockInSearchDTO.getEndAtDate() == null ||
                        !ci.getDateTime().isAfter(clockInSearchDTO.getEndAtDate()))
                .collect(Collectors.toList());

        int total = filtered.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);

        List<ClockInListDTO> paged = filtered.subList(start, end).stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());

        return ClockInResponseWithTotalDTO.builder()
                .items(paged)
                .total((long) total)
                .build();
    }

    @Override
    public ClockInResponseDTO updateClockIn(Long id, ClockInRequestDTO clockInRequestDTO) {
        ClockIn clockIn = clockInRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de ponto não encontrado"));
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(clockInRequestDTO.getFuncionario());
        Employee employee = convertToEntity(employeeDTO);

        clockIn.setDirection(clockInRequestDTO.getSentido());
        clockIn.setEmployee(employee);
        clockIn.setDateTime(LocalDateTime.parse(clockInRequestDTO.getDataHora(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        ClockIn updatedClockIn = clockInRepository.save(clockIn);

        return mapToDTO(updatedClockIn);
    }

    @Override
    public ClockInResponseDTO deleteClockIn(Long id) {
        ClockIn clockIn = clockInRepository.findById(id).orElse(null);
        assert clockIn != null;
        clockInRepository.delete(clockIn);
        return mapToDTO(clockIn);
    }

    private ClockInResponseDTO mapToDTO(ClockIn clockIn) {
        return ClockInResponseDTO.builder()
                .dataHora(clockIn.getDateTime().toString())
                .sentido(clockIn.getDirection())
                .funcionario(mapToFuncionarioDTO(clockIn.getEmployee()))
                .build();
    }

    private ClockInListDTO mapToListDTO(ClockIn clockIn) {
        return ClockInListDTO.builder()
                .id(clockIn.getClockInId())
                .funcionario(mapToFuncionarioListDTO(clockIn.getEmployee()))
                .empresa(new CompanyListDTO(123L, "Empresa"))
                .nomeFuncao("Função")
                .sentido(clockIn.getDirection())
                .dataHora(clockIn.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    private EmployeeResponseDTO mapToFuncionarioDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .idFuncionario(employee.getEmployeeId())
                .nome(employee.getEmployeeName())
                .tipoSanguineo(employee.getBloodType())
                .sexo(employee.getSex())
                .dataNascimento(employee.getBirthDate())
                .build();
    }

    private EmployeeListDTO mapToFuncionarioListDTO(Employee employee) {
        return EmployeeListDTO.builder()
                .idFuncionario(employee.getEmployeeId())
                .nome(employee.getEmployeeName())
                .build();
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setSex(employeeDTO.getSex());
        employee.setBloodType(employeeDTO.getBloodType());
        return employee;
    }
}