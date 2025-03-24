package com.usermanagement.application.service.impl;

import com.usermanagement.application.dto.RoleDTO;
import com.usermanagement.application.exception.ResourceNotFoundException;
import com.usermanagement.application.service.RoleService;
import com.usermanagement.domain.entity.Role;
import com.usermanagement.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = mapToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }

    @Override
    @Transactional
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada com id: " + id));

        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        Role updatedRole = roleRepository.save(role);
        return mapToDTO(updatedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada com id: " + id));
        return mapToDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada com nome: " + name));
        return mapToDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Função não encontrada com id: " + id));
        roleRepository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    private Role mapToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        return role;
    }

    private RoleDTO mapToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        return roleDTO;
    }
}