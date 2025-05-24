package com.sqlutions.altave.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleHours {
    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("total_hours")
    private double totalHours;
}
