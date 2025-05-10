package com.sqlutions.altave.controller;

import com.sqlutions.altave.entity.AnalyticsSummary;
import com.sqlutions.altave.service.AnalyticsSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics-summary")
@RequiredArgsConstructor
public class AnalyticsSummaryController {

    private final AnalyticsSummaryService service;

    @GetMapping
    public List<AnalyticsSummary> getAll() {
        return service.getAllSummaries();
    }
}
