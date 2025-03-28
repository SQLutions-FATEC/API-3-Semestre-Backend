package com.sqlutions.altave.dto;


import com.fasterxml.jackson.annotation.JsonProperty;


public class EmpresaDTO {
    private Long id;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("cnpj")
    private String cnpj;
    @JsonProperty("trade_name")
    private String tradeName;

    public EmpresaDTO() {
    }


    public EmpresaDTO(Long id, String companyName, String cnpj, String tradeName) {
        this.id = id;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.tradeName = tradeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}
