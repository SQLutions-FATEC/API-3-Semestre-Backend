package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClockInResponseWithTotalDTO {
    @JsonProperty("items")
    private List<ClockInListDTO> items;
    @JsonProperty("total")
    private long total;
}