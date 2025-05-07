package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.*;
import com.sqlutions.altave.entity.ClockIn;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.entity.Contract;
import com.sqlutions.altave.repository.ClockInRepository;
import com.sqlutions.altave.repository.ContractRepository;
import com.sqlutions.altave.service.EmployeeService;
import com.sqlutions.altave.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(clockInRequestDTO.getEmployee());
        Employee employee = convertToEntity(employeeDTO);
        ClockIn clockIn = ClockIn.builder()
                .dateTimeIn(LocalDateTime.now())
                .direction(clockInRequestDTO.getDirection())
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
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(clockInRequestDTO.getEmployee());
        Employee employee = convertToEntity(employeeDTO);

        clockIn.setDirection(clockInRequestDTO.getDirection());
        clockIn.setEmployee(employee);
        clockIn.setDateTimeIn(LocalDateTime.parse(clockInRequestDTO.getDateTimeIn(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        clockIn.setDateTimeOut(LocalDateTime.parse(clockInRequestDTO.getDateTimeOut(),
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
                .dateTimeIn(clockIn.getDateTimeIn().toString())
                .direction(clockIn.getDirection())
                .employee(mapToFuncionarioDTO(clockIn.getEmployee()))
                .build();
    }

    private ClockInListDTO mapToListDTO(ClockIn clockIn) {
        var employee = clockIn.getEmployee();

        CompanyListDTO companyDTO = null;
        String roleName = null;

        if (employee != null) {
            Optional<Contract> contractOpt = contractRepository.findContractByEmployeeAndDate(
                    employee, clockIn.getDateTimeIn().toLocalDate());

            if (contractOpt.isPresent()) {
                Contract contract = contractOpt.get();

                if (contract.getCompany() != null) {
                    companyDTO = new CompanyListDTO(
                            contract.getCompany().getId(),
                            contract.getCompany().getCompanyName()
                    );
                }

                if (contract.getRole() != null) {
                    roleName = contract.getRole().getName();
                }
            }
        }

        return ClockInListDTO.builder()
                .id(clockIn.getClockInId())
                .employee(mapToFuncionarioListDTO(employee))
                .company(companyDTO)
                .roleName(roleName)
                .direction(clockIn.getDirection())
                .dateTimeIn(clockIn.getDateTimeIn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }


    private EmployeeResponseDTO mapToFuncionarioDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .idFuncionario(employee.getId())
                .nome(employee.getEmployeeName())
                .tipoSanguineo(employee.getBloodType())
                .sexo(employee.getSex())
                .dataNascimento(employee.getBirthDate())
                .build();
    }

    private EmployeeListDTO mapToFuncionarioListDTO(Employee employee) {
        return EmployeeListDTO.builder()
                .idFuncionario(employee.getId())
                .registerNumber(employee.getRegisterNumber())
                .nome(employee.getEmployeeName())
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
}