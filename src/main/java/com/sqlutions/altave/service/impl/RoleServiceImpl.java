package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.dto.CompanyDTO;
import com.sqlutions.altave.dto.RoleDTO;
import com.sqlutions.altave.entity.Company;
import com.sqlutions.altave.entity.Role;
import com.sqlutions.altave.repository.RoleRepository;
import com.sqlutions.altave.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }
    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoleDTO convertToDTO(Role role) {
        return new RoleDTO(
                role.getRoleId(),
                role.getName()
        );
    }

    private Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return role;
    }
}