package com.sqlutions.altave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDTO {
    private Long id;
    @JsonProperty("name")
    private String name;
}
