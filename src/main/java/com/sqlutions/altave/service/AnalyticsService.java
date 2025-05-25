package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.analytics.AnalyticsDTO;

import java.time.LocalDate;

public interface AnalyticsService {
    AnalyticsDTO getAnalyticsForCompany(
            Long companyId,
            LocalDate startDate,
            LocalDate endDate
    );
}
