package com.usermanagement.application.service;

import com.usermanagement.application.dto.RoleDTO;
import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    RoleDTO getRole(Long id);
    RoleDTO getRoleByName(String name);
    List<RoleDTO> getAllRoles();
    void deleteRole(Long id);
    boolean existsByName(String name);
}