package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.CompanyListDTO;
import com.sqlutions.altave.dto.EmployeeListDTO;
import com.sqlutions.altave.dto.EmployeeResponseDTO;
import com.sqlutions.altave.dto.ClockInListDTO;
import com.sqlutions.altave.dto.ClockInRequestDTO;
import com.sqlutions.altave.dto.ClockInResponseDTO;
import com.sqlutions.altave.dto.ClockInResponseWithTotalDTO;
import com.sqlutions.altave.dto.ClockInSearchDTO;
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
        ClockIn clockIn = ClockIn.builder()
                .dateTime(LocalDateTime.now())
                .direction(clockInRequestDTO.getSentido())
                .employee(employeeService.getEmployeeById(clockInRequestDTO.getFuncionario()).orElse(null))
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
        Pageable pageable = PageRequest.of(page, size);
        Page<ClockIn> movimentacoesPage = clockInRepository.findAll(pageable);

        List<ClockInListDTO> movimentacoes = movimentacoesPage.stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());

        return ClockInResponseWithTotalDTO.builder()
                .items(movimentacoes)
                .total(movimentacoesPage.getTotalElements())
                .build();
    }

    @Override
    public ClockInResponseDTO updateClockIn(Long id, ClockInRequestDTO clockInRequestDTO) {
        ClockIn clockIn = clockInRepository.findById(id).orElse(null);
        assert clockIn != null;
        clockIn.setDirection(clockInRequestDTO.getSentido());
        clockIn.setEmployee(employeeService.getEmployeeById(clockInRequestDTO.getFuncionario()).orElse(null));
        clockIn.setDateTime(LocalDateTime.parse(clockInRequestDTO.getDataHora(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
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
                .nome(employee.getName())
                .tipoSanguineo(employee.getBloodType())
                .sexo(employee.getSex())
                .dataNascimento(employee.getBirthDate())
                .build();
    }

    private EmployeeListDTO mapToFuncionarioListDTO(Employee employee) {
        return EmployeeListDTO.builder()
                .idFuncionario(employee.getEmployeeId())
                .nome(employee.getName())
                .build();
    }
}