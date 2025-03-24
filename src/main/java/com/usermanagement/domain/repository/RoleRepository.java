package com.usermanagement.domain.repository;

import com.usermanagement.domain.entity.Role;
import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
    void delete(Role role);
    void deleteById(Long id);
    boolean existsByName(String name);
}