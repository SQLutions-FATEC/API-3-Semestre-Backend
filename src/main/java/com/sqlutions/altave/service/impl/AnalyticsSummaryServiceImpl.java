package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.entity.AnalyticsSummary;
import com.sqlutions.altave.repository.AnalyticsSummaryRepository;
import com.sqlutions.altave.service.AnalyticsSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsSummaryServiceImpl implements AnalyticsSummaryService {

    private final AnalyticsSummaryRepository repository;

    @Override
    public List<AnalyticsSummary> getAllSummaries() {
        return repository.findAll();
    }
}
