package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClockInTimeUpdateDTO {
    @JsonProperty("date_time")
    private String dataHora;
}
