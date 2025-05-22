package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.analytics.AnalyticsDTO;
import com.sqlutions.altave.dto.analytics.RoleHours;
import com.sqlutions.altave.repository.AnalyticsRepository;
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

    private List<RoleHours> getHoursWorkedByRole(Long companyId) {
        return analyticsRepository.getHoursWorkedByRole(companyId).stream()
                .map(row -> RoleHours.builder()
                        .roleName((String) row[0])
                        .totalHours(((Number) row[1]).doubleValue())
                        .build())
                .collect(Collectors.toList());
    }

    public AnalyticsDTO getAnalyticsForCompany(Long companyId) {

        AnalyticsDTO dto = new AnalyticsDTO();

        dto.setCompanyId(companyId);

        dto.setClockInWithInCount(analyticsRepository.countClockInWithIn(companyId));
        dto.setClockInWithOutCount(analyticsRepository.countClockInWithOut(companyId));
        dto.setHoursWorkedByRole(getHoursWorkedByRole(companyId));
        dto.setMaleWorkers(analyticsRepository.countMaleWorkers(companyId));
        dto.setFemaleWorkers(analyticsRepository.countFemaleWorkers(companyId));

        LocalDateTime now = LocalDateTime.now();

        dto.setExpiringContracts(analyticsRepository.getExpiringContracts(companyId, now.plusMonths(2).toLocalDate()));
        dto.setIncompleteClockIns(analyticsRepository.getIncompleteClockIns(companyId, now.minusHours(48)));

        dto.setMidnightToMorning(analyticsRepository.countMidnightToMorning(companyId));
        dto.setMorningToAfternoon(analyticsRepository.countMorningToAfternoon(companyId));
        dto.setAfternoonToNight(analyticsRepository.countAfternoonToNight(companyId));

        return dto;
    }
}
