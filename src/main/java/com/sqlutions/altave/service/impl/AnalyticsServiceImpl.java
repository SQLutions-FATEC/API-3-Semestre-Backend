package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.analytics.AnalyticsDTO;
import com.sqlutions.altave.dto.analytics.DailyRegisters;
import com.sqlutions.altave.dto.analytics.EmployeeCount;
import com.sqlutions.altave.dto.analytics.EmployeesByShift;
import com.sqlutions.altave.dto.analytics.RoleHours;
import com.sqlutions.altave.repository.AnalyticsRepository;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Autowired
    private CompanyRepository companyRepository;

    LocalDateTime now = LocalDateTime.now();

    private List<RoleHours> getHoursWorkedByRole(Long companyId, LocalDate startDate, LocalDate endDate) {
        return analyticsRepository.getHoursWorkedByRole(companyId, startDate, endDate).stream()
                .map(row -> RoleHours.builder()
                        .roleName((String) row[0])
                        .totalHours(((Number) row[1]).doubleValue())
                        .build())
                .collect(Collectors.toList());
    }

    public AnalyticsDTO getAnalyticsForCompany(Long companyId, LocalDate startDate, LocalDate endDate) {

        return AnalyticsDTO.builder()
                .companyId(companyId)
                .companyName(companyRepository.getTradeNameById(companyId))
                .dailyRegisters(new DailyRegisters(
                        analyticsRepository.countClockInWithIn(companyId, now.minusHours(24)),
                        analyticsRepository.countClockInWithOut(companyId, now.minusHours(24))
                ))
                .hoursWorkedByRole(getHoursWorkedByRole(companyId, startDate, endDate))
                .employeeCount(new EmployeeCount(
                        analyticsRepository.countMaleWorkers(companyId),
                        analyticsRepository.countFemaleWorkers(companyId)
                ))
                .expiringContracts(analyticsRepository.getExpiringContracts(companyId))
                .incompleteClockIns(analyticsRepository.getIncompleteClockIns(companyId, now.minusHours(48)))
                .employeesByShift(new EmployeesByShift(
                        analyticsRepository.countMidnightToMorning(companyId, now.minusWeeks(1)),
                        analyticsRepository.countMorningToAfternoon(companyId, now.minusWeeks(1)),
                        analyticsRepository.countAfternoonToNight(companyId, now.minusWeeks(1))
                ))
                .build();
    }
}
