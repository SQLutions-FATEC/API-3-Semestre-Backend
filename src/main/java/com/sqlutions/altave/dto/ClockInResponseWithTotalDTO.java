package com.sqlutions.altave.dto;

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
    private List<ClockInListDTO> items;
    private long total;
}
