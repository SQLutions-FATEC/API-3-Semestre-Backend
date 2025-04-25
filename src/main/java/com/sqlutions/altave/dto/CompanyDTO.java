package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO {
    private Long id;
    @JsonProperty("name")
    private String companyName;
    @JsonProperty("cnpj")
    private String cnpj;
    @JsonProperty("trade_name")
    private String tradeName;
}
