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
    @JsonProperty("id")
    private Long id;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("cnpj")
    private String cnpj;
    @JsonProperty("trade_name")
    private String tradeName;
}
