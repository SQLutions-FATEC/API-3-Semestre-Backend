package com.sqlutions.altave.controller;

import com.sqlutions.altave.dto.analytics.AnalyticsDTO;
import com.sqlutions.altave.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public AnalyticsDTO getAnalytics(@RequestParam(required = false) Long companyId) {
        return analyticsService.getAnalyticsForCompany(companyId);
    }
}
