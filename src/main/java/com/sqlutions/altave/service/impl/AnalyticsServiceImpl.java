package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.analytics.AnalyticsDTO;
import com.sqlutions.altave.dto.analytics.DailyRegisters;
import com.sqlutions.altave.dto.analytics.EmployeeCount;
import com.sqlutions.altave.dto.analytics.EmployeesByPeriod;
import com.sqlutions.altave.dto.analytics.RoleHours;
import com.sqlutions.altave.repository.AnalyticsRepository;
import com.sqlutions.altave.repository.CompanyRepository;
import com.sqlutions.altave.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private List<RoleHours> getHoursWorkedByRole(Long companyId) {
        return analyticsRepository.getHoursWorkedByRole(companyId, now.minusWeeks(1).toLocalDate()).stream()
                .map(row -> RoleHours.builder()
                        .roleName((String) row[0])
                        .totalHours(((Number) row[1]).doubleValue())
                        .build())
                .collect(Collectors.toList());
    }

    public AnalyticsDTO getAnalyticsForCompany(Long companyId) {

        return AnalyticsDTO.builder()
                .companyId(companyId)
                .companyName(companyRepository.getTradeNameById(companyId))
                .dailyRegisters(new DailyRegisters(
                        analyticsRepository.countClockInWithIn(companyId, now.minusHours(24)),
                        analyticsRepository.countClockInWithOut(companyId, now.minusHours(24))
                ))
                .hoursWorkedByRole(getHoursWorkedByRole(companyId))
                .employeeCount(new EmployeeCount(
                        analyticsRepository.countMaleWorkers(companyId),
                        analyticsRepository.countFemaleWorkers(companyId)
                ))
                .expiringContracts(
                        analyticsRepository.getExpiringContracts(companyId, now.plusMonths(2).toLocalDate())
                )
                .incompleteClockIns(
                        analyticsRepository.getIncompleteClockIns(companyId, now.minusHours(48))
                )
                .employeesByPeriod(new EmployeesByPeriod(
                        analyticsRepository.countMidnightToMorning(companyId),
                        analyticsRepository.countMorningToAfternoon(companyId),
                        analyticsRepository.countAfternoonToNight(companyId)
                ))
                .build();
    }
}
