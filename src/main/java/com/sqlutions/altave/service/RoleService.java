package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    List<RoleDTO> getAllRoles();
}
