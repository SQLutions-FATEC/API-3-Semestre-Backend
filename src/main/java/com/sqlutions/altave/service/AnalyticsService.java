package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.analytics.AnalyticsDTO;

public interface AnalyticsService {
    AnalyticsDTO getAnalyticsForCompany(Long companyId);
}
