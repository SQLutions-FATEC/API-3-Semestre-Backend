package com.sqlutions.altave.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsDTO {

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("daily_registers")
    private DailyRegisters dailyRegisters;

    @JsonProperty("hours_worked_by_role")
    private List<RoleHours> hoursWorkedByRole;

    @JsonProperty("employee_count")
    private EmployeeCount employeeCount;

    @JsonProperty("expiring_contracts")
    private List<ExpiringContract> expiringContracts;

    @JsonProperty("incomplete_clock_ins")
    private List<IncompleteClockIn> incompleteClockIns;

    @JsonProperty("employees_by_period")
    private EmployeesByPeriod employeesByPeriod;
}
