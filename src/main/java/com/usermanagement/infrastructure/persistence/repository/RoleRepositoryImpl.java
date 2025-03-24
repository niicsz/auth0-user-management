package com.usermanagement.infrastructure.persistence.repository;

import com.usermanagement.domain.entity.Role;
import com.usermanagement.domain.repository.RoleRepository;
import com.usermanagement.infrastructure.persistence.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;

    @Autowired
    public RoleRepositoryImpl(JpaRoleRepository jpaRoleRepository) {
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public Role save(Role role) {
        RoleEntity roleEntity = mapToEntity(role);
        RoleEntity savedEntity = jpaRoleRepository.save(roleEntity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRoleRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name).map(this::mapToDomain);
    }

    @Override
    public List<Role> findAll() {
        return jpaRoleRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Role role) {
        jpaRoleRepository.deleteById(role.getId());
    }

    @Override
    public void deleteById(Long id) {
        jpaRoleRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRoleRepository.existsByName(name);
    }

    private RoleEntity mapToEntity(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        entity.setDescription(role.getDescription());
        return entity;
    }

    private Role mapToDomain(RoleEntity entity) {
        Role role = new Role();
        role.setId(entity.getId());
        role.setName(entity.getName());
        role.setDescription(entity.getDescription());
        return role;
    }
}