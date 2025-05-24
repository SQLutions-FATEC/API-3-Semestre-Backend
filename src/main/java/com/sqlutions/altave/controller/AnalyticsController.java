package com.sqlutions.altave.controller;

import io.swagger.v3.oas.annotations.Operation;
import com.sqlutions.altave.dto.analytics.AnalyticsDTO;
import com.sqlutions.altave.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    @Operation(summary = "Endpoint para deletar a foto de um funcionário pelo ID")
    public AnalyticsDTO getAnalytics(
            @RequestParam Long companyId,
            @RequestParam String start_date,
            @RequestParam String end_date
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return analyticsService.getAnalyticsForCompany(
                companyId,
                LocalDate.parse(start_date, formatter),
                LocalDate.parse(end_date, formatter)
        );
    }
}
