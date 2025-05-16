package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.*;
import com.sqlutions.altave.entity.ClockIn;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.exception.ClockInException;
import com.sqlutions.altave.repository.ClockInRepository;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.service.EmployeeService;
import com.sqlutions.altave.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class ClockInServiceImpl implements ClockInService {
    @Autowired
    private ClockInRepository clockInRepository;
    private final EmployeeService employeeService;

    private final ContractRepository contractRepository;

    public ClockInServiceImpl(EmployeeService employeeService, ContractRepository contractRepository) {
        this.employeeService = employeeService;
        this.contractRepository = contractRepository;
    }

    @Override
    public ClockInResponseDTO createClockIn(ClockInRequestDTO clockInRequestDTO) {
        LocalDateTime dateTimeIn = LocalDateTime.parse(clockInRequestDTO.getDateTimeIn(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime dateTimeOut = LocalDateTime.parse(clockInRequestDTO.getDateTimeOut(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (dateTimeIn.isAfter(dateTimeOut)) {
            throw new ClockInException("Data de entrada não pode ser posterior à data de saída");
        }

        EmployeeDTO employeeDTO = employeeService.getEmployeeById(clockInRequestDTO.getEmployee());
        Employee employee = convertToEntity(employeeDTO);

        ClockIn clockIn = ClockIn.builder()
                .dateTimeIn(dateTimeIn)
                .dateTimeOut(dateTimeOut)
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

        List<ClockIn> allClockIns = clockInRepository.findAllByOrderByDateTimeInDesc();

        List<ClockIn> filtered = allClockIns.stream()
                .filter(ci -> clockInSearchDTO.getEmployee() == null ||
                        (ci.getEmployee() != null &&
                                ci.getEmployee().getEmployeeName() != null &&
                                ci.getEmployee().getEmployeeName().toLowerCase()
                                        .contains(clockInSearchDTO.getEmployee().toLowerCase())))
                .filter(ci -> {
                    if (clockInSearchDTO.getCompany() == null && clockInSearchDTO.getRole() == null) {
                        return true;
                    }

                    Optional<Contract> contractOpt = contractRepository.findContractByEmployeeAndDate(
                            ci.getEmployee(), ci.getDateTimeIn().toLocalDate());

                    if (contractOpt.isEmpty()) return false;

                    Contract contract = contractOpt.get();

                    boolean empresaOk = clockInSearchDTO.getCompany() == null ||
                            (contract.getCompany() != null &&
                                    contract.getCompany().getCompanyName() != null &&
                                    contract.getCompany().getCompanyName().toLowerCase()
                                            .contains(clockInSearchDTO.getCompany().toLowerCase()));

                    boolean funcaoOk = clockInSearchDTO.getRole() == null ||
                            (contract.getRole() != null &&
                                    contract.getRole().getName() != null &&
                                    contract.getRole().getName().toLowerCase()
                                            .contains(clockInSearchDTO.getRole().toLowerCase()));

                    return empresaOk && funcaoOk;
                })
                .filter(ci -> clockInSearchDTO.getStartedAtDate() == null ||
                        !ci.getDateTimeIn().isBefore(clockInSearchDTO.getStartedAtDate()))
                .filter(ci -> clockInSearchDTO.getEndAtDate() == null ||
                        !ci.getDateTimeIn().isAfter(clockInSearchDTO.getEndAtDate()))
                .filter(ci -> clockInSearchDTO.getDirection() == null ||
                        (ci.getDirection() != null &&
                                ci.getDirection().equalsIgnoreCase(clockInSearchDTO.getDirection())))

                .filter(ci -> clockInSearchDTO.getMinHours() == null ||
                        (ci.getWorkedHours() != null && ci.getWorkedHours() >= clockInSearchDTO.getMinHours()))
                .filter(ci -> clockInSearchDTO.getMaxHours() == null ||
                        (ci.getWorkedHours() != null && ci.getWorkedHours() <= clockInSearchDTO.getMaxHours()))
                .toList();

        int total = filtered.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);

        List<ClockInListDTO> paged = filtered.subList(start, end).stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());

        return ClockInResponseWithTotalDTO.builder()
                .items(paged)
                .total(total)
                .build();
    }

    @Override
    public ClockInResponseDTO updateClockIn(Long id, ClockInRequestDTO clockInRequestDTO) {
        ClockIn clockIn = clockInRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de ponto não encontrado"));
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(clockInRequestDTO.getEmployee());
        Employee employee = convertToEntity(employeeDTO);

        LocalDateTime newDateTimeIn = LocalDateTime.parse(clockInRequestDTO.getDateTimeIn(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime newDateTimeOut = LocalDateTime.parse(clockInRequestDTO.getDateTimeOut(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (newDateTimeIn.isAfter(newDateTimeOut)) {
            throw new ClockInException("Data de entrada não pode ser posterior à data de saída");
        }

        clockIn.setEmployee(employee);
        clockIn.setDateTimeIn(newDateTimeIn);
        clockIn.setDateTimeOut(newDateTimeOut);

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
                .dateTimeIn(clockIn.getDateTimeIn().toString())
                .dateTimeOut(clockIn.getDateTimeOut() != null ? clockIn.getDateTimeOut().toString() : null)
                .employee(mapToFuncionarioDTO(clockIn.getEmployee()))
                .workedHours(clockIn.getWorkedHours())
                .build();
    }

    private ClockInListDTO mapToListDTO(ClockIn clockIn) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Contract contract = contractRepository.findById(clockIn.getContract().getContractId())
                .orElseThrow(() -> new NoSuchElementException("Contract not found"));

        return ClockInListDTO.builder()
                .id(clockIn.getClockInId())
                .employee(EmployeeListDTO.builder()
                        .idFuncionario(clockIn.getEmployee().getId())
                        .nome(clockIn.getEmployee().getEmployeeName())
                        .registerNumber(clockIn.getEmployee().getRegisterNumber())
                        .build())
                .company(CompanyListDTO.builder()
                        .id(contract.getCompany().getId())
                        .companyName(contract.getCompany().getCompanyName())
                        .build())
                .roleName(contract.getRole().getName())
                .dateTimeIn(clockIn.getDateTimeIn().format(formatter))
                .dateTimeOut(clockIn.getDateTimeOut() != null
                        ? clockIn.getDateTimeOut().format(formatter)
                        : null)
                .workedHours(clockIn.getWorkedHours())
                .build();
    }

    private EmployeeResponseDTO mapToFuncionarioDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .idFuncionario(employee.getId())
                .nome(employee.getEmployeeName())
                .tipoSanguineo(employee.getBloodType())
                .sexo(employee.getSex())
                .dataNascimento(employee.getBirthDate())
                .registerNumber(employee.getRegisterNumber())
                .build();
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setRegisterNumber(employeeDTO.getRegisterNumber());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setSex(employeeDTO.getSex());
        employee.setBloodType(employeeDTO.getBloodType());
        return employee;
    }

    @Override
    public List<ClockInResponseDTO> exportClockIns(ClockInSearchDTO filters) {
        List<ClockIn> allClockIns = clockInRepository.findAllByOrderByDateTimeInDesc();

        List<ClockIn> filtered = allClockIns.stream()
                .filter(ci -> filters.getEmployee() == null ||
                        (ci.getEmployee() != null &&
                                ci.getEmployee().getEmployeeName() != null &&
                                ci.getEmployee().getEmployeeName().toLowerCase()
                                        .contains(filters.getEmployee().toLowerCase())))
                .filter(ci -> {
                    if (filters.getCompany() == null && filters.getRole() == null) {
                        return true;
                    }
                    Optional<Contract> contractOpt = contractRepository.findContractByEmployeeAndDate(
                            ci.getEmployee(), ci.getDateTimeIn().toLocalDate());
                    if (contractOpt.isEmpty()) return false;
                    Contract contract = contractOpt.get();

                    boolean empresaOk = filters.getCompany() == null ||
                            (contract.getCompany() != null &&
                                    contract.getCompany().getCompanyName() != null &&
                                    contract.getCompany().getCompanyName().toLowerCase()
                                            .contains(filters.getCompany().toLowerCase()));

                    boolean funcaoOk = filters.getRole() == null ||
                            (contract.getRole() != null &&
                                    contract.getRole().getName() != null &&
                                    contract.getRole().getName().toLowerCase()
                                            .contains(filters.getRole().toLowerCase()));

                    return empresaOk && funcaoOk;
                })
                .filter(ci -> filters.getStartedAtDate() == null ||
                        !ci.getDateTimeIn().isBefore(filters.getStartedAtDate()))
                .filter(ci -> filters.getEndAtDate() == null ||
                        !ci.getDateTimeIn().isAfter(filters.getEndAtDate()))
                .filter(ci -> filters.getDirection() == null ||
                        (ci.getDirection() != null &&
                                ci.getDirection().equalsIgnoreCase(filters.getDirection())))
                .filter(ci -> filters.getMinHours() == null ||
                        (ci.getWorkedHours() != null && ci.getWorkedHours() >= filters.getMinHours()))
                .filter(ci -> filters.getMaxHours() == null ||
                        (ci.getWorkedHours() != null && ci.getWorkedHours() <= filters.getMaxHours()))
                .toList();

        return filtered.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}